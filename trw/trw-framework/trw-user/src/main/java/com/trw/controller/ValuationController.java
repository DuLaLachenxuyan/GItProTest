package com.trw.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTValuation;
import com.trw.service.IValuationService;
import com.trw.service.ServiceAgentRoleService;
import com.trw.util.StrKit;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 订单评价
 */

@RestController
public class ValuationController extends BaseController {
	@Autowired
	private IValuationService valutionService;
	@Autowired
	private ServiceAgentRoleService serviceAgentRoleService;

	@RequestMapping("/auth/selectMyValution")
	@ApiOperation(httpMethod = "POST", value = "服务中心-个人中心-我的评价", notes = "服务中心-个人中心-我的评价")
	@ApiImplicitParams({ @ApiImplicitParam(name = "agentid", value = "经纪人id", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "angeopinion", value = "评级等级类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> selectMyValution(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> rmg = new ResultMsg<>();
		TrwTAgent agent =ShiroKit.getUser();
		ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectByAgentid(agent.getId());
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTValuation> page = new PageFactory<TrwTValuation>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		if (!StrKit.equals(Constant.ROLE_ADMIN, serviceAgentRole.getRoleId())) {// 经纪人登录
			param.put("agentid", getTokenData().getUserId());
		}
		List<Map<String, Object>> list = valutionService.selectMyValution(page, param);
		map.put("list", list);
		map.put("page", page);
		if (list == null) {
			rmg.setMsg("查询评价失败");
			rmg.setCode(Constant.CODE_FAIL);
		}
		rmg.setMsg("查询成功");
		rmg.setCode(Constant.CODE_SUCC);
		rmg.setData(map);
		return rmg;
	}

	@RequestMapping(value = "/guest/addValution", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "提交评价", notes = "提交评价")
	public ResultMsg<TrwTValuation> addValution(@RequestBody @Valid TrwTValuation valuation, BindingResult result) {
		ResultMsg<TrwTValuation> resultMsg = new ResultMsg<>();
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				resultMsg.setCode(Constant.CODE_FAIL);
				resultMsg.setMsg(error.getDefaultMessage());
				return resultMsg;
			}
		}
		valuation.setUserid(getTokenData().getUserId());
		valuation.setCreatetime(new Date());
		valutionService.insertValution(valuation);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(valuation);
		return resultMsg;
	}
}