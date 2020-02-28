package com.trw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTMsg;
import com.trw.service.IMsgService;
import com.trw.service.ServiceAgentRoleService;
import com.trw.util.CollectionKit;
import com.trw.util.StrKit;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 消息前端控制器
 * </p>
 *
 * @author haochen
 * @since 2018-06-28
 */
@RestController
public class TrwTMsgController extends BaseController {

	@Autowired
	private IMsgService msgService;
	@Autowired
	private ServiceAgentRoleService serviceAgentRoleService;

	@RequestMapping(value = "/guest/getmyMsg")
	@ApiOperation(httpMethod = "POST", value = "查看自己的消息", notes = "查看自己的消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isRead", value = "消息读取状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:createtime-订单时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getmyMsg(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		Page<TrwTMsg> page = new PageFactory<TrwTMsg>().defaultPage();
		String isRead = req.getParameter("isRead");
		Map<String, String> param = getParamMapFromRequest(req);
		String userid = getTokenData().getUserId();
		param.put("userid", userid);
		List<TrwTMsg> listMsg = msgService.findMyMsg(page, param);
		Map<String, Object> map = new HashMap<>();
		// 根据用户接收到的最新消息来判断是已读(01)还是未读(02)
		if (CollectionKit.isNotEmpty(listMsg)) {
			if (StrKit.equals(isRead, "true")) {
				if (!StrKit.equals(listMsg.get(0).getIsRead(), Constant.YES)) {// 看消息是否以前就是 已读(01)
					listMsg.get(0).setIsRead(Constant.YES);// 设置最新消息已读
					//更新最新消息的那一条
					TrwTMsg trwTMsg = new TrwTMsg();
					trwTMsg.setId(listMsg.get(0).getId());
					trwTMsg.setIsRead(listMsg.get(0).getIsRead());
					msgService.updateById(trwTMsg);
				}
			}
			map.put("isRead", listMsg.get(0).getIsRead());
		} else {
			map.put("isRead", Constant.YES);// 没有消息设置消息已读
		}
		map.put("listMsg", listMsg);
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/delMymsg")
	@ApiOperation(httpMethod = "POST", value = "删除自己的消息", notes = "删除自己的消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "信息的主键id", dataType = "string", paramType = "query") })
	public ResultMsg<String> appointment(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		msgService.deleteById(Integer.valueOf(req.getParameter("id")));
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setMsg("删除消息成功");
		return resultMsg;
	}

	@PostMapping(value = "/delMsgs", consumes = { "application/json" })
	@ApiOperation(value = "批量删除消息", notes = "批量删除消息")
	public ResultMsg<String> delMsgs(@RequestBody List<Integer> ids) {
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		if (msgService.deleteBatchIds(ids)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setMsg("批量删除消息成功");
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("批量删除消息失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/selectmyMsg")
	@ApiOperation(httpMethod = "GET", value = "服务中心-查看自己的消息", notes = "服务中心-查看自己的消息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "starttime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> selectmyMsg(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		Page<TrwTMsg> page = new PageFactory<TrwTMsg>().defaultPage();
		Map<String, String> param = getParamMapFromRequest(req);
		TrwTAgent agent = ShiroKit.getUser();
		ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectByAgentid(agent.getId());
		// 判断当前角色是否为服务商
		if ("1".equals(serviceAgentRole.getRoleId())) {
			param.put("userid", req.getParameter("faciid"));
		} else if ("2".equals(serviceAgentRole.getRoleId())) { // 判断当前角色是否为经纪人
			param.put("userid", agent.getId());
		} else {
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		List<TrwTMsg> listMsg = msgService.findMyMsg(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("listMsg", listMsg);
		map.put("page", page);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}
}
