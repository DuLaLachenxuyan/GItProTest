package com.trw.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.annotion.CountScore;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.AgentFeignApi;
import com.trw.feign.FaciFeignApi;
import com.trw.feign.MsgFeignApi;
import com.trw.feign.UserFeignApi;
import com.trw.manage.ShiroKit;
import com.trw.model.Records;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTLookinfo;
import com.trw.model.TrwTReport;
import com.trw.model.TrwTUser;
import com.trw.service.ILandService;
import com.trw.service.IReportService;
import com.trw.service.RecordsService;
import com.trw.util.BeanUtil;
import com.trw.util.BusinessUtil;
import com.trw.util.DateUtil;
import com.trw.util.PCAConvert;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;
import com.trw.vo.SoilSource;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 土地产品
 */
@RestController
public class LandController extends BaseController {
    
    @Autowired
    private ILandService landService;
    @Autowired
    private FaciFeignApi faciFeignApi;
    @Autowired
    private MsgFeignApi producer;
    @Autowired
    private IReportService reportService;
    @Autowired
    private UserFeignApi userFeignApi;
    @Autowired
    private RecordsService recordsService;
    @Autowired
    private AgentFeignApi agentFeignApi;
    
    
    @RequestMapping(value = "/getLand")
    @ApiOperation(httpMethod = "POST", value = "土地列表查询", notes = "土地列表查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transMode", value = "流转方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landLabel", value = "土地标签", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "purpose", value = "土地用途", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landPlaceMax", value = "土地面积最大值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landPlaceMin", value = "土地面积最小值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "groundSource", value = "地源情况(02-待交易，03-已交易)", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "priceMax", value = "土地价格最大值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "priceMin", value = "土地价格最小值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "rentYearMin", value = "流转年限最小值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "rentYearMax", value = "流转年限最大值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段:reporttime-发布时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> getLand(HttpServletRequest req) {
        Map<String, String> param = getParamMapFromRequest(req);
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> list = landService.selectMainLand(page, param);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", page.getTotal());
        ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
        msg.setCode(Constant.CODE_SUCC);
        msg.setData(map);
        return msg;
    }
    
    @RequestMapping(value = "/getLandDetail")
    @ApiOperation(httpMethod = "POST", value = "土地详情", notes = "土地详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productId", value = "土地商品Id", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> getLandDetail(HttpServletRequest req) {
        String productId = req.getParameter("productId");
        Wrapper<TrwTLand> wrapper = new EntityWrapper<TrwTLand>().eq("productid", productId);
        TrwTLand land = landService.selectOne(wrapper);
        SoilSource soilSource = new SoilSource();
        BeanUtil.copyPropertiesNotForce(soilSource, land);
        if (!StrKit.isBlank(land.getFaciid())) {
            TrwTFaci tFaci = (TrwTFaci) redisService.get(Constant.FACI + land.getFaciid());
            soilSource.setTFaci(tFaci);
        }
        // 查询收藏次数
        ResultMsg<Integer> enshrine = userFeignApi.getEnshrineNum(productId);
        List<TrwTLookinfo> looks = landService.selectLookModel(productId);
        soilSource.setLooks(looks);
        String delookMonery = redisService.getString(Constant.CONFIGPIX + "delookMonery");
        // 平台电话
        String adminPhone = redisService.getString(Constant.CONFIGPIX + "admin_phone");
        // 平台联系人
        String adminName = redisService.getString(Constant.CONFIGPIX + "admin_name");
        soilSource.setDelookMonery(delookMonery);
        Map<String, Object> map = new HashMap<>();
        map.put("land", soilSource);
        map.put("admin_phone", adminPhone);
        map.put("admin_name", adminName);
        map.put("enshrine", enshrine.getData());
        ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
        msg.setCode(Constant.CODE_SUCC);
        msg.setData(map);
        return msg;
    }
    
    @RequestMapping(value = "/guest/postLand")
    @ApiOperation(httpMethod = "POST", value = "发布土地", notes = "发布土地")
    public ResultMsg<TrwTLand> postLand(@ApiParam(value = "土地参数", required = true) @RequestBody @Valid TrwTLand land, BindingResult result) {
        ResultMsg<TrwTLand> resultMsg = new ResultMsg<TrwTLand>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                resultMsg.setCode(Constant.CODE_FAIL);
                resultMsg.setMsg(error.getDefaultMessage());
                return resultMsg;
            }
        }
        TokenData tokenData = (TokenData) request.getAttribute("tokenData");
        
        ResultMsg<TrwTFaci> rs = faciFeignApi.getFaciByLoc(land.getLocation());
        TrwTFaci faci = rs.getData();
        
        land.setReporttime(LocalDateTime.now());
        land.setReporter(tokenData.getUserId());
        land.setEarthSourceNote(Constant.YES);
        land.setLandState(Constant.CLIENT_APPROVE);
        land.setProductid(idGenerate.generateSeq(Constant.PREFIXLDP));
        String imgs = land.getImgs();
        if (!StrKit.isBlank(imgs)) {
            land.setCoverimg(imgs.split(",")[0]);
        }
        boolean flg = landService.saveLand(land);
        try {
            if (flg && Constant.YES.equals(land.getProxy())) { // 成功向服务上发送短信
                // 通过区域获取服务中心发送通知
                if (faci == null) {
                    // 给平台发信息通知
                    faci = BusinessUtil.getPlatFaci();
                }
                JSONObject smsJson = new JSONObject();
                smsJson.put("name", faci.getContacts());
                smsJson.put("region", PCAConvert.me().convert(land.getLocation()));
                smsJson.put("name2", land.getLandContact());
                smsJson.put("tel", land.getPhone());
                smsJson.put("demand", Constant.LANDMSG);
                String template = smsJson.toJSONString();
                // 处理消息
                String postMsg = redisService.getString(Constant.CONFIGPIX + "postMsg");
                NoticeMsg msg = new NoticeMsg();
                msg.setMsgId(postMsg);
                msg.setPhone(faci.getMobile());
                msg.setParam(template);
                msg.setUserId(faci.getFaciid());
                msg.setNeedSMS(Constant.YES);
                producer.sendMessage(msg);
            }
            resultMsg.setCode(Constant.CODE_SUCC);
            resultMsg.setData(land);
            return resultMsg;
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setMsg(e.getMessage());
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        
        
    }
    
    @RequestMapping(value = "/guest/myLands")
    @ApiOperation(httpMethod = "POST", value = "我的土地", notes = "我的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", required = true, paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", required = true, paramType = "query")})
    public ResultMsg<Page<TrwTLand>> myLands(HttpServletRequest req) {
        TokenData tokenData = (TokenData) req.getAttribute("tokenData");
        ResultMsg<Page<TrwTLand>> msg = new ResultMsg<Page<TrwTLand>>();
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        Wrapper<TrwTLand> wrapper = new EntityWrapper<TrwTLand>().eq("reporter", tokenData.getUserId());
        Page<TrwTLand> land = landService.selectPage(page, wrapper);
        msg.setCode(Constant.CODE_SUCC);
        msg.setData(land);
        return msg;
    }
    
    @RequestMapping(value = "/guest/reportLand")
    @ApiOperation(httpMethod = "POST", value = "举报土地", notes = "举报土地")
    @CountScore(rule = "2")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "举报土地id", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "content", value = "举报内容", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务中心Id", dataType = "string", paramType = "query")
    })
    public ResultMsg<String> reportLand(HttpServletRequest req) {
        ResultMsg<String> resultMsg = new ResultMsg<>();
        
        TokenData token = (TokenData) req.getAttribute("tokenData");
        String userId = token.getUserId();
        
        TrwTReport report = new TrwTReport();
        ResultMsg<TrwTUser> userData = userFeignApi.getUser(userId);
        TrwTUser user = userData.getData();
        String userName = user.getName();
        String content = req.getParameter("content");
        String productid = req.getParameter("productid");
        
        String faciid = req.getParameter("faciid");
        req.setAttribute("faciid", faciid);
        
        report.setProductid(productid);
        report.setUsername(userName);
        report.setContent(content);
        boolean flg = reportService.insert(report);
        if (flg) {
            resultMsg.setMsg("举报成功");
            resultMsg.setCode(Constant.CODE_SUCC);
        }
        return resultMsg;
    }
    
    @RequestMapping(value = "/guest/getMyLands")
    @ApiOperation(httpMethod = "POST", value = "用户我的土地-土地管理", notes = "服务中心-土地管理-我的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landState", value = "土地状态", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transMode", value = "流转类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "earthSourceNote", value = "上架情况", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landPlaceMin", value = "土地面积输入框第一个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landPlaceMax", value = "土地面积输入框第二个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "timeOne", value = "时间框第一个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "timeTwo", value = "时间框第二个值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> getMyLands(HttpServletRequest req) {
        Map<String, String> parm = getParamMapFromRequest(req);
        TokenData tokenData = (TokenData) request.getAttribute("tokenData");
        String userId = tokenData.getUserId();
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        
        parm.put("reporter", userId);
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> myLandsManage = landService.selectMyLandsManage(page, parm);
        Map<String, Object> result = new HashMap<>();
        result.put("lands", myLandsManage);
        result.put("total", page.getTotal());
        resultMsg.setData(result);
        resultMsg.setMsg(Constant.CODE_SUCC);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectFaciDetail")
    @ApiOperation(httpMethod = "POST", value = "根据标签查询土地", notes = "根据标签查询土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landNote", value = "土地标签，01为待交易的，02为已完成的", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")
    })
    public ResultMsg<List<TrwTLand>> selectFaciDetail(HttpServletRequest req) {
        ResultMsg<List<TrwTLand>> resultMsg = new ResultMsg<>();
        String landNote = req.getParameter("landNote");
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> faciLand = landService.selectLandNote(page, landNote);
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(faciLand);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectFaciLand")
    @ApiOperation(httpMethod = "POST", value = "服务中心土地列表", notes = "服务中心土地列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "faciid", value = "服务中心", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> selectFaciLand(@RequestParam("faciid") String faciid) {
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        List<TrwTLand> faciLand = landService.selectFaciLand(page, faciid);
        Map<String, Object> map = new HashMap<>();
        map.put("faciLand", faciLand);
        map.put("total", page.getTotal());
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
        
    }
    
    @RequestMapping(value = "/auth/getMyFaciLand")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地", notes = "服务中心-我的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landState", value = "地源情况", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "agencySituation", value = "代理情况", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "aFCOAstatus", value = "申请取消代理状况", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transMode", value = "流转类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "location", value = "区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "联系方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phoneCheck", value = "电话核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fieldCheck", value = "现场核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "withoutCheck", value = "未核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productid", value = "土地编号", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "agentId", value = "经纪人ID", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreageMax", value = "土地面积最大值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreageMin", value = "土地面积最小值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "account", value = "土地来源", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "reporttimeone", value = "发布时间第一个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "reporttimetwo", value = "发布时间第二个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "updatetimeone", value = "更新时间第一个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "updatetimetwo", value = "更新时间第二个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "text", value = "输入框内容", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务商ID", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "needid", value = "客源ID", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> getMyFaciLand(HttpServletRequest req) {
        Map<String, String> param = getParamMapFromRequest(req);
        Page<SoilSource> page = new PageFactory<SoilSource>().defaultPage();
        TrwTAgent agent = ShiroKit.getUser();
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        
        // 判断当前角色是否为经纪人管理员
        if (!ShiroKit.hasRole(Constant.ROLE_ADMIN)) {
            param.put("agentId", agent.getId());
        }
        // 查询土地列表
        List<SoilSource> sourceDetails = landService.selectSourceDetails(page, param);
        
        Map<String, Object> data = new HashMap<>();
        data.put("sourceDetails", sourceDetails);
        data.put("total", page.getTotal());
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(data);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/getLandMarket")
    @ApiOperation(httpMethod = "POST", value = "服务中心-土地市场", notes = "服务中心-土地市场")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "groundSource", value = "土地状态", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landState", value = "土地状态", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "agencySituation", value = "代理情况", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "location", value = "区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "联系方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "purpose", value = "土地用途", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreageMax", value = "土地面积最大值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreageMin", value = "土地面积最小值", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "reporttimeone", value = "发布时间第一个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "reporttimetwo", value = "发布时间第二个框", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "applyAgent", value = "可否申请代理", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "search", value = "搜索", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String, Object>> getLandMarket(HttpServletRequest req) {
        Map<String, String> param = getParamMapFromRequest(req);
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Map<String, Object> data = new HashMap<>();
        TrwTFaci faci = (TrwTFaci) redisService.get(Constant.FACI + param.get("faciid"));
        if (faci != null) {
            String faciLocation = faci.getLocation(); // 经纪人中心位置
            param.put("faciLocation", faciLocation);
        }
        // 土地详情
        List<Map<String, Object>> marketDetails = landService.selectLandMarket(page, param);
        data.put("marketDetails", marketDetails);
        data.put("total", page.getTotal());
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(data);
        return resultMsg;
        
    }
    
    @RequestMapping(value = "/auth/updateMyland")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-编辑", notes = "服务中心-我的土地-编辑")
    @ApiImplicitParams({@ApiImplicitParam(name = "location", value = "所在地区", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "address", value = "详细地址", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productname", value = "信息标题", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreage", value = "土地面积", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "rentyear", value = "流转年限", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transMode", value = "流转方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "price", value = "交易费用", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "imgs", value = "图片", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productDesc", value = "土地描述", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landContact", value = "土地所有人", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "ownership", value = "权属方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "reporttime", value = "发布时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landFacilities", value = "土地设施", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "siteProperties", value = "场地属性", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "awayAirport", value = "距机场", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "awayExpressway", value = "距高速", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "nationalHighway", value = "距国道", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "speedRailway", value = "距高铁", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "awayPort", value = "距港口", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "awayCity", value = "距城市", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "righttype", value = "证权类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "rightexpirdate", value = "证权有效期", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "watersupply", value = "供水条件", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "powersupply", value = "供电条件", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "netsupply", value = "网络条件", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "mechanization", value = "机械化程度", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "soil", value = "土质", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "irrigation", value = "灌溉条件", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "drainage", value = "排水条件", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phsoil", value = "土壤酸碱度", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "flatness", value = "平整度", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "aum", value = "所在城市年收入", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "cityrate", value = "城市化率", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "residentpop", value = "常驻人口", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "neargroup", value = "附近产业群", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "highway", value = "国道高速", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productid", value = "土地编号", required = true, dataType = "string", paramType = "query")})
    public ResultMsg<String> updateMyland(HttpServletRequest req) {
        ResultMsg<String> resultMsg = new ResultMsg<>();
        Map<String, String> parm = getParamMapFromRequest(req);
        TrwTAgent agent = ShiroKit.getUser();
        parm.put("operator", agent.getId());
        parm.put("operatorTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:MM:ss"));
        
        String imgs = req.getParameter("imgs");
        if (!StrKit.isBlank(imgs)) {
            parm.put("coverimg",imgs.split(",")[0]);
        }
        Boolean flg = landService.updateMyland(parm);
        if (flg == true) {
            resultMsg.setMsg("修改成功");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        } else {
            resultMsg.setMsg("修改失败");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        
    }
    
    @RequestMapping(value = "/auth/selectMyLand")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-查询土地详情", notes = "服务中心-我的土地-查询土地详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地ID", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectMyLand(HttpServletRequest req) {
        String productid = req.getParameter("productid");
        TrwTLand land = landService.selectById(productid);
        Map<String, Object> data = new HashMap<>();
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Page<Records> page = new PageFactory<Records>().defaultPage();
        Map<String, String> map = getParamMapFromRequest(req);
        if (StrKit.isEmpty(land.getAgentId())) {
            List<Records> selectRecords = recordsService.selectBylandid(page, map);
            data.put("selectMyLand", land);
            data.put("selectRecords", selectRecords);
            resultMsg.setCode(Constant.CODE_SUCC);
            resultMsg.setData(data);
            return resultMsg;
        } else {
            ResultMsg<TrwTAgent> trwTAgent = agentFeignApi.getAgentById(land.getAgentId());
            TrwTAgent getAgen = trwTAgent.getData();
            List<Records> selectRecords = recordsService.selectBylandid(page, map);
            data.put("selectMyLand", land);
            data.put("getAgen", getAgen);
            data.put("selectRecords", selectRecords);
            data.put("total", page.getTotal());
            resultMsg.setCode(Constant.CODE_SUCC);
            resultMsg.setData(data);
            return resultMsg;
        }
    }
    
    @RequestMapping(value = "/auth/updateMlLandType")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-标签功能", notes = "服务中心-我的土地-标签功能")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地ID", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "earthSource", value = "地源情况标签", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phoneCheck", value = "电话核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fieldCheck", value = "现场核实", dataType = "string", paramType = "query"),})
    public ResultMsg<TrwTLand> updateMlLandType(HttpServletRequest req) {
        ResultMsg<TrwTLand> resultMsg = new ResultMsg<>();
        String productid = req.getParameter("productid");
        TrwTLand trwTLand = landService.selectById(productid);
        String earthSource = req.getParameter("earthSource");
        TrwTAgent agent = ShiroKit.getUser();
        trwTLand.setOperator(agent.getId());
        trwTLand.setOperatorTime(LocalDateTime.now());
        // 根据点击的按钮执行相应的方法
        if (Constant.EARTH_UPLAND.equals(earthSource)) {
            trwTLand.setUpdatetime(LocalDateTime.now());
            landService.updateById(trwTLand);
            resultMsg.setMsg("刷新土地成功");
        } else if (Constant.EARTH_PAUSE.equals(earthSource)) {
            if ("02".equals(trwTLand.getEarthSourceNote())) {
                trwTLand.setEarthSourceNote(Constant.YES);
                trwTLand.setLandState(Constant.CLIENT_PAYING);//02为待交易
                EntityWrapper<TrwTLand> wrapper = new EntityWrapper<TrwTLand>();
                wrapper.where("productid={0}", trwTLand.getProductid());
                landService.update(trwTLand, wrapper);
                resultMsg.setMsg("取消暂停");
            } else {
                trwTLand.setEarthSourceNote(Constant.NO);
                trwTLand.setLandState(Constant.CLIENT_STOP);//05为暂停土地
                EntityWrapper<TrwTLand> wrapper = new EntityWrapper<TrwTLand>();
                wrapper.where("productid={0}", trwTLand.getProductid());
                landService.update(trwTLand, wrapper);
                resultMsg.setMsg("暂停土地成功");
            }
        } else if (Constant.EARTH_APPROVE.equals(earthSource)) {
            trwTLand.setPhoneCheck(req.getParameter("phoneCheck"));
            trwTLand.setFieldCheck(req.getParameter("fieldCheck"));
            trwTLand.setReviewer(agent.getId());
            trwTLand.setReviewtime(LocalDateTime.now());
            landService.updateById(trwTLand);
            resultMsg.setMsg("修改核实情况成功");
        } else {
            resultMsg.setMsg("错误");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(trwTLand);
        return resultMsg;
    }
    
    @ApiOperation(httpMethod = "POST", value = "首页关键字搜索土地", notes = "首页关键字搜索土地")
    @RequestMapping(value = "/selectMainLand")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "areaid", value = "区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "text", value = "关键字", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectMainLand(HttpServletRequest req) {
        Map<String, String> param = getParamMapFromRequest(req);
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> faciLand = landService.selectMainLand(page, param);
        Map<String, Object> map = new HashMap<>();
        map.put("faciLand", faciLand);
        map.put("total", page.getTotal());
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
        
    }
    
    /**
     * @param @param  productid
     * @param @return 参数说明
     * @return TrwTLand 返回类型
     * @throws @Title: getLandById
     * @Description: 根据ID查询土地
     * @author luojing
     * @date 2018年7月4日
     */
    
    @RequestMapping(value = "/getLandById")
    @ApiOperation(httpMethod = "POST", value = "根据土地id查询土地", notes = "根据土地id查询土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地ID", required = true, dataType = "string", paramType = "query")})
    public ResultMsg<TrwTLand> getLandById(@RequestParam("productid") String productid) {
        ResultMsg<TrwTLand> result = new ResultMsg<TrwTLand>();
        if (StrKit.isNotEmpty(productid)) {
            TrwTLand land = landService.getLandInfo(productid);
            if (land != null) {
                result.setCode(Constant.CODE_SUCC);
                result.setData(land);
            } else {
                result.setCode(Constant.CODE_FAIL);
                result.setData(null);
            }
        } else {
            result.setCode(Constant.CODE_FAIL);
            result.setData(null);
        }
        return result;
    }
    
    @RequestMapping(value = "/auth/updateAllTime")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "服务中心-土地市场-批量刷新", notes = "服务中心-土地市场-批量刷新")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productids", value = "多个土地ID", required = true, dataType = "string", paramType = "query"),})
    public ResultMsg<String> updateAllTime(HttpServletRequest req) {
        String productids = req.getParameter("productids");
        String[] productid = productids.split(",");
        TrwTAgent agent = ShiroKit.getUser();
        List<TrwTLand> list = new ArrayList<>();
        for (int i = 0; i < productid.length; i++) {
            TrwTLand trwTLand = new TrwTLand();
            trwTLand.setUpdatetime(LocalDateTime.now());
            trwTLand.setProductid(productid[i]);
            trwTLand.setOperator(agent.getId());
            trwTLand.setOperatorTime(LocalDateTime.now());
            list.add(trwTLand);
        }
        landService.updateBatchById(list);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("批量刷新成功！");
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
    }
    
    /**
     * 根据id修改土地信息
     *
     * @param trwTLand
     * @return
     */
    @RequestMapping(value = "/updateByid")
    @ApiOperation(httpMethod = "POST", value = "根据id修改土地信息", notes = "根据id修改土地信息")
    public ResultMsg<String> updateByid(@RequestBody TrwTLand trwTLand) {
        TrwTAgent agent = ShiroKit.getUser();
        trwTLand.setOperator(agent.getId());
        trwTLand.setOperatorTime(LocalDateTime.now());
        landService.updateById(trwTLand);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("修改成功");
        return resultMsg;
    }
    
    /**
     * 用户修改土地信息
     *
     * @param trwTLand
     * @return
     */
    @RequestMapping(value = "/guest/updateLand")
    @ApiOperation(httpMethod = "POST", value = "修改土地信息", notes = "修改土地信息")
    public ResultMsg<String> updateLand(@RequestBody TrwTLand trwTLand) {
        ResultMsg<String> resultMsg = new ResultMsg<>();
        TokenData tokenData = (TokenData) request.getAttribute("tokenData");
        trwTLand.setOperator(tokenData.getUserId());
        trwTLand.setOperatorTime(LocalDateTime.now());
        landService.updateById(trwTLand);
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("修改成功");
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/addFaciPostLand")
    @ApiOperation(httpMethod = "POST", value = "服务中心发布土地", notes = "服务中心发布土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landType", value = "土地类型", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "purpose", value = "土地用途", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "location", value = "所在区域", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreage", value = "土地面积", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "ownership", value = "权属类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "rentyear", value = "流转年限", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transMode", value = "流转方式", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "price", value = "土地价格", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productname", value = "土地标题", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "address", value = "详细地址", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productDesc", value = "土地介绍", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landContact", value = "土地联系人", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "files", value = "多张图片地址", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "联系电话", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "proxy", value = "是否代理", dataType = "string", paramType = "query")})
    public ResultMsg<TrwTLand> addFaciPostLand(HttpServletRequest req) {
        ResultMsg<TrwTLand> resultMsg = new ResultMsg<TrwTLand>();
        Map<String, String> param = getParamMapFromRequest(req);
        String price = param.get("price");
        String purpose = param.get("purpose");
        String acreage = param.get("acreage");
        String location = param.get("location");
        if (StrKit.isBlank(location)) {
            resultMsg.setMsg("所在区域必输");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        TrwTAgent agent = ShiroKit.getUser();
        
        if (StrKit.isBlank(price)) {
            param.put("price", "0");
        }
        if (StrKit.isBlank(acreage)) {
            param.put("acreage", "0");
        }
        TrwTLand land = null;
        ResultMsg<TrwTFaci> rs = faciFeignApi.getFaciByLoc(location);
        TrwTFaci faci = rs.getData();
        
        land = BeanUtil.mapToObject(param, TrwTLand.class);
        land.setReporttime(LocalDateTime.now());
        land.setReporter(agent.getId());
        land.setEarthSourceNote(Constant.YES);
        land.setLandState(Constant.CLIENT_APPROVE);
        land.setPurpose(purpose);
        land.setAgentId(agent.getId());
        land.setFaciid(faci.getFaciid());
        land.setProductid(idGenerate.generateSeq(Constant.PREFIXLDP));
        String imgs = param.get("files");
        if (!StrKit.isBlank(imgs)) {
            land.setCoverimg(imgs.split(",")[0]);
            land.setImgs(imgs);
        }
        TrwTAllocation trwTAllocation = new TrwTAllocation();
        trwTAllocation.setId(ToolUtil.generateId("ALL"));
        trwTAllocation.setAgentId(agent.getId());
        trwTAllocation.setProductid(land.getProductid());
        trwTAllocation.setOperator(agent.getId());
        trwTAllocation.setOperatorTime(LocalDateTime.now());
        
        landService.addLand(land, trwTAllocation);
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("发布成功");
        resultMsg.setData(land);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectLandDetailLand")
    @ApiOperation(httpMethod = "POST", value = "土地详情下面推荐的土地", notes = "土地详情下面推荐的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "recommendAreas", value = "推荐区域/01为pc端,02为web端", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectLandDetailLand(HttpServletRequest req) {
        Map<String, Object> param = new HashMap<>();
        if (StrKit.isNotEmpty(req.getParameter("faciid"))) {
            param.put("faciid", req.getParameter("faciid"));
        }
        param.put("currentTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:MM:ss"));
        String recommendAreas = req.getParameter("recommendAreas");
        if ("02".equals(recommendAreas)) {
            param.put("recommendPosition", "06");// 推荐位置，03为土地详情下面得推荐
            param.put("recommendAreas", "02");// 推荐区域，01为pc端
        }
        if ("01".equals(recommendAreas)) {
            param.put("recommendPosition", "03");// 推荐位置，03为土地详情下面得推荐
            param.put("recommendAreas", "01");// 推荐区域，01为pc端
        }
        param.put("order", req.getParameter("order"));
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> lands = landService.selectIndexLand(page, param);
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Map<String, Object> map = new HashMap<>();
        map.put("lands", lands);
        map.put("total", page.getTotal());
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectIndexLand")
    @ApiOperation(httpMethod = "POST", value = "条件查询首页推荐的土地", notes = "条件查询首页推荐的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "landType", value = "土地类型", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段:reporttime-发布时间", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "qery"),
        @ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "recommendAreas", value = "推荐区域/01为pc端,02为web端", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectIndexLand(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<>();
        String recommendAreas = request.getParameter("recommendAreas");
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        //土地信息
        param.put("landType", request.getParameter("landType"));
        param.put("sort", request.getParameter("sort"));
        param.put("order", request.getParameter("order"));
        param.put("areaid",request.getParameter("areaid"));
        param.put("currentTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:MM:ss"));
        if ("02".equals(recommendAreas)) {
            param.put("recommendPosition", "04");//推荐位置，04为web端首页推荐
        }
        if ("01".equals(recommendAreas)) {
            param.put("recommendPosition", "01");//推荐位置，01为pc端首页推荐
        }
        param.put("recommendAreas", recommendAreas);//推荐区域，01为pc端,02为web端
        List<TrwTLand> lands = landService.selectIndexLand(page, param);
        Map<String, Object> data = new HashMap<>();
        data.put("lands", lands);
        data.put("total", page.getTotal());
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        resultMsg.setData(data);
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectFaciRecomLand")
    @ApiOperation(httpMethod = "POST", value = "查询服务商下面推荐的土地", notes = "查询服务商下面推荐的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "qery"),
        @ApiImplicitParam(name = "recommendAreas", value = "区域/01为pc端,02为web端", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectFaciRecomLand(HttpServletRequest request) {
        String faciid = request.getParameter("faciid");
        String recommendAreas = request.getParameter("recommendAreas");
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        if (StrKit.isBlank(faciid)) {
            resultMsg.setMsg("服务商id不能为空");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
        Map<String, Object> param = new HashMap<>();
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        param.put("currentTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:MM:ss"));
        if ("02".equals(recommendAreas)) {
            param.put("recommendPosition", "05");//推荐位置，05为web端服务商首页推荐
        }
        if ("01".equals(recommendAreas)) {
            param.put("recommendPosition", "02");//推荐位置，02为pc端服务商首页推荐
        }
        param.put("recommendAreas", recommendAreas);//推荐区域，01为pc端.02为web端
        param.put("faciid", request.getParameter("faciid"));
        List<TrwTLand> faciRecomLand = landService.selectIndexLand(page, param);
        Map<String, Object> data = new HashMap<>();
        data.put("FaciRecomLand", faciRecomLand);
        data.put("total", page.getTotal());
        resultMsg.setData(data);
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
    }
    
    @RequestMapping(value = "/selectLandDetailLike")
    @ApiOperation(httpMethod = "POST", value = "土地详情下面推猜你喜欢的土地", notes = "土地详情下面推猜你喜欢的土地")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "price", value = "土地价格", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "acreage", value = "土地面积", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "location", value = "区域", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String, Object>> selectLandDetailLike(HttpServletRequest req) {
        String location = req.getParameter("location");
        String areaid = location.substring(0, 4);
        Map<String, String> parm = getParamMapFromRequest(req);
        parm.put("areaid", areaid);
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        List<TrwTLand> selectLandDetailLike = landService.selectMainLand(page, parm);
        Map<String, Object> map = new HashMap<>();
        map.put("selectLandDetailLike", selectLandDetailLike);
        map.put("total", page.getTotal());
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功");
        resultMsg.setData(map);
        return resultMsg;
    }
    
    /**
     * @param @param  req
     * @param @return 参数说明
     * @return ResultMsg<Map<String,Object>> 返回类型
     * @throws @date 2018年8月20日 下午4:25:30
     * @Title: getAgentLandById
     * @Description: 查询经纪人对应的土地(包括服务中心的管理)
     * @author haochen
     */
    @RequestMapping(value = "/auth/getAgentLandById")
    @ApiOperation(httpMethod = "POST", value = "查询经纪人对应的土地(包括服务中心的管理)", notes = "查询经纪人对应的土地(包括服务中心的管理)")
    @ApiImplicitParams({@ApiImplicitParam(name = "faciid", value = "服务中心", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),})
    public ResultMsg<Map<String, Object>> getAgentLandById(HttpServletRequest req) {
        ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
        Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
        Map<String, String> param = getParamMapFromRequest(req);
        TrwTAgent agent = ShiroKit.getUser();
        if (!ShiroKit.hasRole(Constant.ROLE_ADMIN)) {
            param.put("agentId", agent.getId());
        }
        List<TrwTLand> faciLand = landService.selectAgentLandById(page, param);
        Map<String, Object> map = new HashMap<>();
        map.put("faciLand", faciLand);
        map.put("total", page.getTotal());
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
        
    }
    
    /**
     * @param @param  faciid
     * @param @return 参数说明
     * @return ResultMsg<List<TrwTLand>> 返回类型
     * @throws
     * @Title: selectLandTitle
     * @Description: 查询已匹配经纪人未交易的土地
     * @author gongzhen
     * @date 2018年8月20日
     */
    @RequestMapping(value = "/auth/selectLandTitle", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "查询已匹配经纪人未交易的土地", notes = "查询已匹配经纪人的土地")
    public ResultMsg<List<TrwTLand>> selectLandTitle(@RequestParam("faciid") String faciid) {
        ResultMsg<List<TrwTLand>> resultMsg = new ResultMsg<>();
        List<TrwTLand> list = landService.selectLandTitle(faciid);
        if (list == null) {
            resultMsg.setCode(Constant.CODE_FAIL);
            resultMsg.setMsg("没有找到数据");
            return resultMsg;
        }
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功");
        resultMsg.setData(list);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/updateLandApproval")
    @ApiOperation(httpMethod = "POST", value = "服务中心-土地审核", notes = "服务中心-土地审核")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phoneCheck", value = "电话核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fieldCheck", value = "现场核实", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "landState", value = "土地状态", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "text", value = "跟进内容", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "productid", value = "土地ID", dataType = "string", paramType = "query")})
    public ResultMsg<String> updateLandApproval(HttpServletRequest req) {
        TrwTAgent agent = ShiroKit.getUser();
        TrwTLand land = new TrwTLand();
        land.setProductid(req.getParameter("productid"));
        land.setPhoneCheck(req.getParameter("phoneCheck"));
        land.setFieldCheck(req.getParameter("fieldCheck"));
        land.setLandState(req.getParameter("landState"));
        land.setOperator(agent.getId());
        land.setOperatorTime(LocalDateTime.now());
        
        Records records = new Records();
        records.setId(req.getParameter("productid"));
        records.setText(req.getParameter("text"));
        records.setFollowTime(new Date());
        records.setFollowPerson(agent.getId());
        records.setOperatorTime(LocalDateTime.now());
        records.setOperator(agent.getId());
        records.setFollowid(ToolUtil.generateId("FOL"));
        
        boolean flg = landService.addLandApproval(land, records);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        if (flg == true) {
            resultMsg.setMsg("审核成功");
            resultMsg.setCode(Constant.CODE_SUCC);
            return resultMsg;
        } else {
            resultMsg.setMsg("审核失败");
            resultMsg.setCode(Constant.CODE_FAIL);
            return resultMsg;
        }
    }
}
