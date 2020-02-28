package com.trw.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.trw.manage.ShiroKit;
import com.trw.model.TrwTAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.CustomerFeignApi;
import com.trw.feign.MsgFeignApi;
import com.trw.model.FaciRecommend;
import com.trw.model.LandClient;
import com.trw.model.Recommend;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.service.ILandService;
import com.trw.service.RecommendService;
import com.trw.util.BusinessUtil;
import com.trw.util.DateUtil;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.NoMatchLand;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 我的土地-推荐土地
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-28
 */
@RestController
public class RecommendController extends BaseController {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CustomerFeignApi customerFeignApi;
    @Autowired
    private ILandService landservice;
    @Autowired
    private MsgFeignApi msgFeignApi;
    
    @RequestMapping(value = "/guest/selectRecommendLand")
    @ApiOperation(httpMethod = "POST", value = "查询我的土地-推广土地", notes = "查询我的土地-推广土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recommendArea", value = "推荐区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "text", value = "输入框内容", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "timeOne", value = "时间框第一个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "timeTwo", value = "时间框第二个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "token", value = "tokenData", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> selectRecommendLand(HttpServletRequest req) {
        Map<String, String> parm = getParamMapFromRequest(req);
        TokenData token = (TokenData) req.getAttribute("tokenData");
        parm.put("userId", token.getUserId());
        //01为推荐土地
        parm.put("types", "01");
        Page<Recommend> page = new PageFactory<Recommend>().defaultPage();
        List<Recommend> getRecommendLand = recommendService.selectRecommend(page, parm);
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Map<String, Object> map = new HashMap<>();
        map.put("getRecommendLand", getRecommendLand);
        map.put("total", page.getTotal());
        resultMsg.setMsg(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/addMatchCustomer")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-匹配客户", notes = "服务中心-我的土地-匹配客户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地ID", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "clientIds", value = "多个客户id，以逗号分隔", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query")})
    
    public ResultMsg<String> addMatchCustomer(HttpServletRequest request) {
        TrwTAgent agent = ShiroKit.getUser();
        String clientIds = request.getParameter("clientIds");
        ResultMsg<String> resultMsg = new ResultMsg<>();
        String[] clientid = clientIds.split(",");
        List<Recommend> list = new ArrayList<>();
        for (int i = 0; i < clientid.length; i++) {
            ResultMsg<LandClient> landClientMsg = customerFeignApi.getClientById(clientid[i]);
            LandClient landClient = landClientMsg.getData();
            Recommend recommend = new Recommend();
            recommend.setProductid(request.getParameter("productid"));
            recommend.setRemdlandDetails(landClient.getMtitle());
            recommend.setUserId(landClient.getReporter());
            recommend.setNeedIds(clientid[i]);
            recommend.setTypes("02"); //推荐给用户的
            recommend.setRemdlandID(ToolUtil.generateId("REC"));
            recommend.setRecommendTime(DateUtil.getTime());
            recommend.setOperatorTime(LocalDateTime.now());
            recommend.setOperator(agent.getId());
            list.add(recommend);
        }
        Boolean flg = recommendService.insertBatch(list);
        try {
            if (flg) {
                TrwTFaci trwTFaci = (TrwTFaci) redisService.get(Constant.FACI + request.getParameter("faciid"));
                if (trwTFaci == null) {
                	trwTFaci = BusinessUtil.getPlatFaci();
                }
                for (int i = 0; i < clientid.length; i++) {
                    ResultMsg<LandClient> landClientMsg = customerFeignApi.getClientById(clientid[i]);
                    LandClient landClient = landClientMsg.getData();
                    if (landClient == null) {
                        logger.error(clientid[i] + "不存在");
                        continue;
                    }
                    JSONObject smsJson = new JSONObject();
                    smsJson.put("name", landClient.getLandContact());
                    String template = smsJson.toJSONString();
                    // 处理消息
                    String customerAndLand = redisService.getString(Constant.CONFIGPIX + "customerAndLand");
                    NoticeMsg msg = new NoticeMsg();
					msg.setMsgId(customerAndLand);
					msg.setPhone(landClient.getPhone());
					msg.setParam(template);
					msg.setUserId(landClient.getReporter());
					msg.setNeedSMS(Constant.NO);
					msgFeignApi.sendMessage(msg); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setMsg(e.getMessage());
            return resultMsg;
        }
        resultMsg.setMsg("匹配客户成功！");
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/addMatchLand")
    @ApiOperation(httpMethod = "POST", value = "服务中心-客源管理-匹配地源", notes = "服务中心-客源管理-匹配地源")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "needid", value = "客源ID", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productids", value = "多个土地id，以逗号分隔", dataType = "string", paramType = "query")
    })
    public ResultMsg<List<Recommend>> addMatchLand(HttpServletRequest request) {
        ResultMsg<List<Recommend>> resultMsg = new ResultMsg<>();
        TrwTAgent agent = ShiroKit.getUser();
        String productids = request.getParameter("productids");
        String[] productid = productids.split(",");
        List<Recommend> list = new ArrayList<>();
        String needid = request.getParameter("needid");
        
        ResultMsg<LandClient> landClientMsg = customerFeignApi.getClientById(needid);
        LandClient landClient = landClientMsg.getData();
        if (landClient == null) {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg(needid + "不存在");
            return resultMsg;
        }
        String psource = landClient.getPsource();  //客户来源
        //判断客户类型，是否能够匹配地源
        if (StrKit.equals("02", psource)) {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg("不能给自有客户匹配地源");
            return resultMsg;
        }
        String userID = landClient.getReporter();
        for (String s : productid) {
            Recommend recommend = new Recommend();
            TrwTLand land = landservice.selectById(s);
            if (land == null) {
                resultMsg.setMsg("地源不存在！");
                resultMsg.setCode(Constant.CODE_FAIL);
                return resultMsg;
            }
            recommend.setNeedIds(needid);
            recommend.setRemdlandDetails(land.getProductname());
            recommend.setUserId(userID);
            recommend.setRecommendTime(DateUtil.getTime());
            recommend.setLandState(land.getLandState());
            recommend.setTypes("03");//给客户匹配土地
            recommend.setRemdlandID(ToolUtil.generateId("REC"));
            recommend.setProductid(s);
            recommend.setOperator(agent.getId());
            recommend.setOperatorTime(LocalDateTime.now());
            list.add(recommend);
        }
        //将匹配的地源插入匹配表
        Boolean flg = recommendService.insertBatch(list);
        if (flg) {
            resultMsg.setMsg("匹配地源成功！");
            resultMsg.setCode(Constant.CODE_SUCC);
        } else {
            resultMsg.setCode(Constant.CODE_FAIL);
        }
        return resultMsg;
    }
    
    @RequestMapping("/guest/getRecommendLand")
    @ApiOperation(httpMethod = "POST", value = "个人中心-我的需求-推荐的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landState", value = "土地状态", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "minTime", value = "最小时间", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "maxTime", value = "最大时间", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", required = true, paramType = "query")
    })
    public ResultMsg<Map<String, Object>> getRecommendLand(HttpServletRequest req) {
        ResultMsg<Map<String, Object>> rmg = new ResultMsg<>();
        TokenData token = (TokenData) req.getAttribute("tokenData");
        Map<String, String> parm = getParamMapFromRequest(req);
        parm.put("userId", token.getUserId());
        Page<Recommend> page = new PageFactory<Recommend>().defaultPage();
        List<Recommend> lands = recommendService.selectRecommendLand(page, parm);
        Map<String, Object> result = new HashMap<>();
        result.put("lands", lands);
        result.put("total", page.getTotal());
        rmg.setMsg(Constant.CODE_SUCC);
        rmg.setData(result);
        return rmg;
    }
    
    @RequestMapping("/guest/delRecommendLand")
    @ApiOperation(httpMethod = "POST", value = "删除推荐的土地", notes = "根据id删除推荐的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "remdlandID", value = "推荐的主键id", dataType = "string", paramType = "query")
    })
    public ResultMsg<String> delRecommendLand(HttpServletRequest req) {
        ResultMsg<String> resultMsg = new ResultMsg<String>();
        String id = req.getParameter("remdlandID");
        boolean flg = recommendService.deleteById(id);
        if (!flg) {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg("删除推荐土地失败");
            return resultMsg;
        }
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("删除推荐土地成功");
        return resultMsg;
    }
    
    @RequestMapping("/auth/addFacitRecommend")
    @ApiOperation(httpMethod = "POST", value = "服务中心-新增土地推荐")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地id", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "productname", value = "土地标题", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "reporter", value = "土地发布人id", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "recommendArea", value = "推荐区域", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "position", value = "推荐位置", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "regional", value = "推荐地区", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "recommendTime", value = "推荐时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "expirationTime", value = "到期时间", dataType = "string", paramType = "query")
    })
    public ResultMsg<String> addFacitRecommend(HttpServletRequest request) {
        TrwTAgent agent = ShiroKit.getUser();
        String recommendTime = request.getParameter("recommendTime");
        String expirationTime = request.getParameter("expirationTime");
        String position=request.getParameter("position");
        ResultMsg<String> resultMsg = new ResultMsg<>();
        if (StrKit.isBlank(recommendTime) || StrKit.isBlank(expirationTime)) {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg("时间不能为空");
            return resultMsg;
        }
        Date recommendtime = DateUtil.parse(recommendTime, "yyyy-MM-dd");
        Date expirationtime = DateUtil.parse(expirationTime, "yyyy-MM-dd");
        /*if (recommendtime.before(new Date())) {
            resultMsg.setMsg("推荐日期必须在今天之后");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }*/
        if (expirationtime.before(new Date()) || expirationtime.before(recommendtime)) {
            resultMsg.setMsg("到期日期必须在今天之后");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        Map<String, String> parm = new HashMap<>();
        parm.put("recommendArea", request.getParameter("recommendArea"));
        parm.put("position", request.getParameter("position"));
        parm.put("currentTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        parm.put("productid", request.getParameter("productid"));
        parm.put("types", "01");//01为推荐的土地
        List<Recommend> list = recommendService.selectUnrdedRecommend(parm);
        if (list.size() == 0 || list == null) {
            Recommend recommend = new Recommend();
            recommend.setRemdlandID(ToolUtil.generateId("Rem"));
            if(Constant.YES.equals(position) || "04".equals(position)){
                recommend.setState("04");//04为推荐审核
            }else {
                recommend.setState(Constant.YES);//01为已推荐
            }
            recommend.setTypes(Constant.YES);//01为推荐的土地
            recommend.setProductid(request.getParameter("productid"));
            recommend.setRemdlandDetails(request.getParameter("productname"));
            recommend.setUserId(request.getParameter("reporter"));
            recommend.setRecommendArea(request.getParameter("recommendArea"));
            recommend.setPosition(request.getParameter("position"));
            recommend.setRegional(request.getParameter("regional"));
            recommend.setRecommendTime(DateUtil.getTime(recommendtime));
            recommend.setExpirationTime(expirationtime);
            recommend.setOperatorTime(LocalDateTime.now());
            recommend.setOperator(agent.getId());
            recommendService.insertData(parm, recommend);
            resultMsg.setMsg("推荐成功");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        }
        resultMsg.setMsg("该土地已在该区域推荐");
        resultMsg.setCode(Constant.CODE_FAIL);
        return resultMsg;
    }
    
    @RequestMapping("/auth/selectFacitRecommend")
    @ApiOperation(httpMethod = "POST", value = "服务中心-查询推荐土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "state", value = "推荐状态", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "productname", value = "土地标题", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "area", value = "推荐地区", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "recommendArea", value = "推荐区域", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "position", value = "推荐位置", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "recommendStartTime", value = "推荐开始时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "recommendEndTime", value = "推荐结束时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "expirationStartTimeend", value = "到期开始时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "expirationEndTime", value = "到期结束时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectFacitRecommend(HttpServletRequest req) {
        Map<String, String> parm = getParamMapFromRequest(req);
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        if (StrKit.isBlank(parm.get("faciid"))) {
            resultMsg.setMsg("服务商id不能为空");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        if ("02".equals(parm.get("state"))) {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            parm.put("currentTime", currentTime);
            parm.put("state", "");
        }
        Page<FaciRecommend> page = new PageFactory<FaciRecommend>().defaultPage();
        List<FaciRecommend> faciRecommends = recommendService.selectFacitRecommend(page, parm);
        Map<String, Object> map = new HashMap<>();
        map.put("faciRecommends", faciRecommends);
        map.put("total", page.getTotal());
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
    }
    
    @RequestMapping("/auth/updateRecommend")
    @ApiOperation(httpMethod = "POST", value = "服务中心-取消推荐")
    @ApiImplicitParam(name = "remdlandID", value = "推荐id", dataType = "String", paramType = "query")
    public ResultMsg<String> updateRecommend(HttpServletRequest req) {
        String remdlandID=req.getParameter("remdlandID");
        TrwTAgent agent = ShiroKit.getUser();
        Recommend selectRecommend=recommendService.selectById(remdlandID);
        Recommend recommend = new Recommend();
        recommend.setRemdlandID(remdlandID);
        recommend.setOperator(agent.getId());
        recommend.setOperatorTime(LocalDateTime.now());
        ResultMsg<String> resultMsg = new ResultMsg<>();
        if ("03".equals(selectRecommend.getState())){
            recommend.setState("01");//01为已推荐
            recommendService.updateByid(recommend);
            resultMsg.setMsg("已推荐");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        }else if ("01".equals(selectRecommend.getState())){
            recommend.setState("03");//01为取消推荐
            recommendService.updateByid(recommend);
            resultMsg.setMsg("已取消");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        }else {
            resultMsg.setMsg("失败");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
    }
    
    @RequestMapping("/selectLandByNeedId")
    @ApiOperation(httpMethod = "POST", value = "查询根据需求推荐的土地")
    @ApiImplicitParam(name = "needIds", value = "推荐id", dataType = "String", paramType = "query")
    public ResultMsg<Map<String, Object>> selectLandByNeedId(HttpServletRequest req) {
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        String needIds = req.getParameter("needIds");
        Map<String, String> map = new HashMap<>();
        map.put("needIds", needIds);
        List<TrwTLand> landByNeedId = landservice.selectLandByNeedId(page, map);
        Map<String, Object> parm = new HashMap<>();
        parm.put("landByNeedId", landByNeedId);
        parm.put("total", page.getTotal());
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(parm);
        return resultMsg;
    }
    
    @RequestMapping("/auth/selectMatchLand")
    @ApiOperation(httpMethod = "GET", value = "查询客户未被匹配过的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "needId", value = "推荐id", dataType = "String", paramType = "query")
    })
    public ResultMsg<List<NoMatchLand>> selectMatchLand(HttpServletRequest req) {
        ResultMsg<List<NoMatchLand>> resultMsg = new ResultMsg<>();
        Map<String, String> parm = getParamMapFromRequest(req);
        List<NoMatchLand> list = recommendService.selectMatchLand(parm);
        for (NoMatchLand n : list) {
            if (!StrKit.isBlank(n.getNeedIds())) {
                n.setFlg("01"); //当needid不为空设置标志为01,表示该土地已匹配
            } else {
                n.setFlg("02");//当needid不为空，土地未匹配
            }
        }
        resultMsg.setMsg("查询成功");
        resultMsg.setData(list);
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
        
    }
    
    @RequestMapping("/auth/selectUnrdedLand")
    @ApiOperation(httpMethod = "POST", value = "查询未推荐过的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query")
    })
    public ResultMsg<List<TrwTLand>> selectUnrdedLand(HttpServletRequest req) {
        Map<String, String> map = getParamMapFromRequest(req);
        List<TrwTLand> unrdedLand = recommendService.selectUnrdedLand(map);
        ResultMsg<List<TrwTLand>> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功");
        resultMsg.setData(unrdedLand);
        return resultMsg;
    }
    
    @RequestMapping("/auth/selectMatchClient")
    @ApiOperation(httpMethod = "POST", value = "查询未匹配过的客户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地id", dataType = "String", paramType = "query")
    })
    public ResultMsg<List<LandClient>> selectMatchClient(HttpServletRequest req) {
        Map<String, String> map = getParamMapFromRequest(req);
        List<LandClient> matchClient = recommendService.selectMatchClient(map);
        ResultMsg<List<LandClient>> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功");
        resultMsg.setData(matchClient);
        return resultMsg;
    }
    
    @RequestMapping("/getLandRecommend")
    @ApiOperation(httpMethod = "POST", value = "查询土地是否被推荐过")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地id", dataType = "String", paramType = "query")
    })
    public ResultMsg<String> getLandRecommend(HttpServletRequest request) {
        Map<String, String> map = getParamMapFromRequest(request);
        map.put("currentTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        List<Recommend> list = recommendService.getLandRecommend(map);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        if (list == null || list.size() == 0) {
            resultMsg.setMsg("未推荐");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        } else {
            resultMsg.setMsg("已推荐");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        }
    }
    
    @RequestMapping("/guest/delFacitRecommend")
    @ApiOperation(httpMethod = "POST", value = "删除推荐位中的土地")
    @ApiImplicitParam(name = "remdlandID", value = "推荐的id", dataType = "String", paramType = "query")
    public ResultMsg<String> delFacitRecommend(HttpServletRequest req) {
        String remdlandID = req.getParameter("remdlandID");
        Boolean flg = recommendService.deleteById(remdlandID);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        if (flg == true) {
            resultMsg.setCode(Constant.CODE_SUCC);
            resultMsg.setMsg("删除成功");
            return resultMsg;
        } else {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg("删除失败");
            return resultMsg;
        }
    }
    
}
