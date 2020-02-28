package com.trw.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.model.ServiceScoreRule;
import com.trw.service.ServiceScoreRuleService;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
@RestController
public class ServiceScoreRuleController extends BaseController {
    @Autowired
    private ServiceScoreRuleService serviceScoreRuleService;
    
    @RequestMapping(value = "/auth/getServiceScoreRule")
    @ApiOperation(httpMethod = "POST", value = "查询积分规则", notes = "查询积分规则")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务商", dataType = "string", paramType = "query")})
    public ResultMsg<Page<ServiceScoreRule>> getServiceScoreRule(HttpServletRequest req) {
        Page<ServiceScoreRule> page = new PageFactory<ServiceScoreRule>().defaultPage();
        Page<ServiceScoreRule> serviceScoreRule = serviceScoreRuleService.selectPage(page);
        ResultMsg<Page<ServiceScoreRule>> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(serviceScoreRule);
        return resultMsg;
    }
    
}

