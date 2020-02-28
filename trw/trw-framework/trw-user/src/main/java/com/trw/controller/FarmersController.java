package com.trw.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.model.PfaFFarmers;
import com.trw.service.IPfaFFarmersService;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linhai123
 * @since 2018-07-18
 */
@RestController
public class FarmersController extends BaseController {
    @Autowired
    private IPfaFFarmersService iPfaFFarmersService;
    
    @RequestMapping(value = "/selectFarmerMain")
    @ApiOperation(httpMethod = "POST", value = "大国农匠首页", notes = "大国农匠首页")
    public ResultMsg<Map<String,Object>> selectFarmerMain(HttpServletRequest request) {
        //查询首页轮播推荐的农匠
        List<PfaFFarmers> farmerRecommend=iPfaFFarmersService.selectRecommend();
        //查询优秀技术团队
        
        Map<String,Object> map=new HashMap<>();
        map.put("farmerRecommend",farmerRecommend);
        ResultMsg<Map<String,Object>> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功！");
        resultMsg.setData(map);
        return resultMsg;
    }
    
    
    @RequestMapping(value = "/selectFarmerIndex")
    @ApiOperation(httpMethod = "POST", value = "大国农匠-二级页面", notes = "大国农匠首页-二级页面")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "location", value = "所在区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "farmerType", value = "农匠类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "farmerTransMode", value = "农匠流转方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transModeUse", value = "农匠流转用途", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")})
    public ResultMsg<Map<String,Object>> selectFarmerIndex(HttpServletRequest req){
        Map<String,String> parm=getParamMapFromRequest(req);
        //查询首页轮播推荐的农匠
        List<PfaFFarmers> farmerRecommend=iPfaFFarmersService.selectRecommend();
    
        //条件查询首页推荐的农匠
        Page<PfaFFarmers> page = new PageFactory<PfaFFarmers>().defaultPage();
        List<PfaFFarmers> pfaFFarmersRecommend = iPfaFFarmersService.selectMin(page,parm);
    
        Map<String,Object> map=new HashMap<>();
        map.put("farmerRecommend",farmerRecommend);
        map.put("pfaFFarmersRecommend",pfaFFarmersRecommend);
        map.put("total",page.getTotal());
        ResultMsg<Map<String,Object>> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功！");
        resultMsg.setData(map);
        return resultMsg;
    }
    
    @RequestMapping(value = "/getFarmerById")
    @ApiOperation(httpMethod = "POST", value = "农匠详情", notes = "农匠详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "农匠id", dataType = "string", paramType = "query")
       })
    public ResultMsg<PfaFFarmers> getFarmerById(@RequestParam("id") String id){
        PfaFFarmers farmersDetails=iPfaFFarmersService.selectById(id);
        ResultMsg<PfaFFarmers> resultMsg=new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("查询成功！");
        resultMsg.setData(farmersDetails);
        return resultMsg;
    }
    
    /*@RequestMapping(value = "/add")
    @ApiOperation(httpMethod = "POST", value = "大国农匠添加", notes = "大国农匠添加")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "农匠姓名", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "avatar", value = "头像", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "idcard", value = "身份证号码", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "companyName", value = "所在单位名称", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "position", value = "职位", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "eduback", value = "学历", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "workingAddress", value = "工作地址", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "registeredResidence", value = "户口所在地", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "engagedIndustry", value = "从事行业", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "lengthService", value = "工龄", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "scale", value = "规模（亩、头、只）", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "introduction", value = "自我介绍", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "location", value = "所在区域", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "farmerType", value = "农匠类型", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "farmerTransMode", value = "农匠流转方式", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "transModeUse", value = "流转方式用途", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "certification", value = "认证", dataType = "string", paramType = "query")
    })
    public ResultMsg add(HttpServletRequest request) {
        ResultMsg resultMsg = new ResultMsg();
        PfaFFarmers pfaFFarmers = new PfaFFarmers();
        pfaFFarmers.setId(ToolUtil.generateId("pfa"));
        pfaFFarmers.setName(request.getParameter("name"));
        pfaFFarmers.setSex(request.getParameter("sex"));
        pfaFFarmers.setAvatar(request.getParameter("avatar"));
        pfaFFarmers.setIdcard(request.getParameter("idcard"));
        pfaFFarmers.setCompanyName(request.getParameter("companyName"));
        pfaFFarmers.setPosition(request.getParameter("position"));
        pfaFFarmers.setEduback(request.getParameter("eduback"));
        pfaFFarmers.setWorkingAddress(request.getParameter("workingAddress"));
        pfaFFarmers.setRegisteredResidence(request.getParameter("registeredResidence"));
        pfaFFarmers.setLengthService(request.getParameter("lengthService"));
        pfaFFarmers.setScale(request.getParameter("scale"));
        pfaFFarmers.setPhone(request.getParameter("phone"));
        pfaFFarmers.setIntroduction(request.getParameter("introduction"));
        pfaFFarmers.setLocation(request.getParameter("location"));
        pfaFFarmers.setFarmerType(request.getParameter("farmerType"));
        pfaFFarmers.setFarmerTransMode(request.getParameter("farmerTransMode"));
        pfaFFarmers.setTransModeUse(request.getParameter("transModeUse"));
        pfaFFarmers.setCertification(request.getParameter("certification"));
        
        iPfaFFarmersService.insert(pfaFFarmers);
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("添加成功");
        return resultMsg;
    }*/
    
    
}

