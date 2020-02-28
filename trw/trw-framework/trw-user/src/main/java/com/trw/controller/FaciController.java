package com.trw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.LandFeignApi;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTFaci;
import com.trw.service.IAgentService;
import com.trw.service.IFaciService;
import com.trw.service.ServiceAgentRoleService;
import com.trw.util.StrKit;
import com.trw.vo.OrderFaci;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
@RestController
public class FaciController extends BaseController {

	@Autowired
	private IFaciService faciService;
	@Autowired
	private LandFeignApi landFeiApi;
	@Autowired
	private IAgentService agentService;
	@Autowired
	private ServiceAgentRoleService serviceAgentRoleService;

	@RequestMapping(value = "/auth/selectInformation")
	@ApiOperation(httpMethod = "POST", value = "根据登录的用户查询对应的信息", notes = "根据登录的用户查询对应的信息")
	public ResultMsg<Map<String, Object>> selectInformation(HttpServletRequest req) {
		TrwTAgent agent =ShiroKit.getUser();
		String agentId = agent.getId();
		ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectByAgentid(agentId);
		Map<String, Object> parm = new HashMap<>();
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		// 判断当前角色是否为服务商
		if ("1".equals(serviceAgentRole.getRoleId())) {
			TrwTAgent trwTAgents = agentService.selectById(agentId);
			// 查询服务商的信息
			TrwTFaci trwTFaci = (TrwTFaci) redisService.get(Constant.FACI + trwTAgents.getFaciid());
			parm.put("information", trwTFaci);
		} else {
			// 查询经纪人信息
			TrwTAgent trwTAgent = agentService.selectById(agentId);
			// 查询服务商的信息
			TrwTFaci trwTFaci = (TrwTFaci) redisService.get(Constant.FACI + trwTAgent.getFaciid());
			parm.put("information", trwTAgent);
			parm.put("trwTFaci", trwTFaci);
		}
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(parm);
		return resultMsg;
	}

	@RequestMapping(value = "/selectFaciList")
	@ApiOperation(httpMethod = "POST", value = "加盟中心浏览", notes = "加盟中心浏览")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query") 
	})
	public ResultMsg<Map<String, Object>> selectFaciList(@RequestParam(required=false,value = "areaid") String areaid) {
		Map<String, String> param = new HashMap<>();
		Page<TrwTFaci> page = new PageFactory<TrwTFaci>().defaultPage();
		param.put("areaid", areaid);
		List<TrwTFaci> list = faciService.selectMainFaci(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", page.getTotal());
		ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}

	@RequestMapping(value = "/getFaciDetail")
	@ApiOperation(httpMethod = "POST", value = "加盟商详情", notes = "加盟商详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "faciid", value = "加盟商ID", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getFaciDetail(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		String faciid = req.getParameter("faciid");
		// 查询加盟商详情
		TrwTFaci faci = (TrwTFaci) redisService.get(Constant.FACI + faciid);
		// 查询对应加盟商的土地信息
		ResultMsg<Map<String, Object>> faciLand = landFeiApi.selectFaciLand(faciid);
		// 查询经纪人信息
		Page<TrwTAgent> page1 = new PageFactory<TrwTAgent>().defaultPage();
		List<Map<String, Object>> trwTAgents = agentService.selectAgent(page1, faciid);
		// 查询经纪人和土地的相关信息
		Page<OrderFaci> page2 = new PageFactory<OrderFaci>().defaultPage();
		// 服务中心的评价
		List<OrderFaci> orderFaci = faciService.selectOrderFaci(page2, faciid);

		Map<String, Object> data = faciLand.getData();
		data.put("faci", faci);
		data.put("trwTAgents", trwTAgents);
		data.put("orderFaci", orderFaci);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(data);
		return resultMsg;
	}

	@RequestMapping(value = "/getFaciByLoc")
	@ApiOperation(httpMethod = "POST", value = "根据区域获取一个服务中心", notes = "根据区域获取一个服务中心")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "location", value = "区域id", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTFaci> getFaciByLoc(@RequestParam("location") String location) {
		TrwTFaci faci = faciService.getFaciByLoc(location);
		ResultMsg<TrwTFaci> resultMsg = new ResultMsg<>();
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(faci);
		return resultMsg;
	}

	@PostMapping(value = "/getFaciByfaciId")
	@ApiOperation(httpMethod = "POST", value = "根据加盟商ID查询加盟商信息", notes = "根据加盟商ID查询加盟商信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "faciId", value = "加盟商ID", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTFaci> getFaciByfaciId(@RequestParam("faciId") String faciId) {
		ResultMsg<TrwTFaci> result = new ResultMsg<TrwTFaci>();
		if (StrKit.isNotEmpty(faciId)) {

			TrwTFaci faci = (TrwTFaci) redisService.get(Constant.FACI + faciId);
			if (faci == null) {
				faci = faciService.getFaci(faciId);
				redisService.set(Constant.FACI + faciId, faci);
			}
			if (faci != null) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(faci);
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

	@RequestMapping(value = "/auth/updateDeposit")
	@ApiOperation(httpMethod = "POST", value = "是否启动保证金看地", notes = "是否启动保证金看地")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "deposit", value = "是否启动保证金看地(01启动保证金看地,02不启动保证金看地)", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTFaci> updateDeposit(HttpServletRequest req) {
		ResultMsg<TrwTFaci> resultMsg = new ResultMsg<TrwTFaci>();
		TrwTAgent agent = ShiroKit.getUser();
		if (StrKit.isBlank(agent.getFaciid())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该经纪人对应的服务商id为空");
			return resultMsg;
		}
		TrwTFaci faci = new TrwTFaci();
		faci.setDeposit(req.getParameter("deposit"));
		faci.setFaciid(agent.getFaciid());
		if (faciService.updateById(faci)) {
			redisService.set(Constant.FACI + faci.getFaciid(), faciService.selectById(faci.getFaciid()));
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(faci);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("修改失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/findMyself")
	@ApiOperation(httpMethod = "POST", value = "服务中心-个人中心-我的账户-服务中心信息", notes = "服务中心-个人中心-我的账户-服务中心信息")
	public ResultMsg<Map<String, Object>> findMyself(HttpServletRequest req) {
		TrwTAgent agent = ShiroKit.getUser();
		ResultMsg<Map<String, Object>> rmg = new ResultMsg<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();

		// 查询经纪人角色
		ServiceAgentRole role = serviceAgentRoleService.selectByAgentid(agent.getId());
		if (role == null) {
			rmg.setMsg("该用户没有角色");
			rmg.setCode(Constant.CODE_FAIL);
		}
		String arId = role.getArId();
		// 如果角色id为1返回服务商信息
		switch (arId) {
		case "1":
			// String faciid = agent.getFaciid();
			// 服务商管理者信息
			map.put("agent", agent);
			// 查询服务商
			TrwTFaci faci = (TrwTFaci) redisService.get(Constant.FACI + agent.getFaciid());
			map.put("faci", faci);
			rmg.setData(map);
			rmg.setCode(Constant.CODE_SUCC);
			break;
		case "2":
			map.put("agent", agent);
			rmg.setData(map);
			rmg.setCode(Constant.CODE_SUCC);
			break;
		default:
			break;
		}
		return rmg;
	}

	@RequestMapping(value = "/auth/updateFaciMsg")
	@ApiOperation(httpMethod = "POST", value = "修改服务中心信息", notes = "修改服务中心信息")
	public ResultMsg<TrwTFaci> updateFaciMsg(@RequestBody TrwTFaci faci) {
		ResultMsg<TrwTFaci> resultMsg = new ResultMsg<TrwTFaci>();
		TrwTAgent agent = ShiroKit.getUser();
		if (StrKit.isBlank(agent.getFaciid())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该经纪人没有对应的服务商");
			return resultMsg;
		}
		faci.setFaciid(agent.getFaciid());
		if (faciService.updateById(faci)) {
			redisService.set(Constant.FACI + faci.getFaciid(), faciService.selectById(faci.getFaciid()));
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(faci);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("修改失败");
		return resultMsg;
	}

	@RequestMapping(value = "/getFaciList")
	@ApiOperation(httpMethod = "POST", value = "手机端-搜索服务中心", notes = "手机端-搜索服务中心")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "mainName", value = "关键字搜索", dataType = "string", paramType = "query"), })
	public ResultMsg<Map<String,Object>> getFaciList(HttpServletRequest req) {
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTFaci> page = new PageFactory<TrwTFaci>().defaultPage();
		List<TrwTFaci> list = faciService.selectMainFaci(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", page.getTotal());
		ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}
	
}
