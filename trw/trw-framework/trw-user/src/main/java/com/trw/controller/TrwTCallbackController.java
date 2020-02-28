package com.trw.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTCallback;
import com.trw.service.ITrwTCallbackService;
import com.trw.util.StrKit;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 预约回电
 * 
 * 
 * </p>
 * 
 * @author chenhao
 * @since 2018-06-23
 */
@RestController
public class TrwTCallbackController extends BaseController {

	@Autowired
	private ITrwTCallbackService callbackService;

	@RequestMapping(value = "/addCallback")
	@ApiOperation(httpMethod = "POST", value = "提交预约回电", notes = "提交预约回电")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "土地id(存在的id)", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务中心id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "agentid", value = "经纪人id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callname", value = "预约回电名字", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callbacktel", value = "预约回电电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "短信验证码", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTCallback> addCallback(HttpServletRequest req) {
		ResultMsg<TrwTCallback> resultMsg = new ResultMsg<TrwTCallback>();

		String productid = req.getParameter("productid");
		String faciid = req.getParameter("faciid");
		String agentid = req.getParameter("agentid");
		String callname = req.getParameter("callname");
		String callbacktel = req.getParameter("callbacktel");
		String code = req.getParameter("code");

		if (StrKit.isBlank(callname)) {
			resultMsg.setMsg("名字不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(callbacktel)) {
			resultMsg.setMsg("电话不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(code)) {
			resultMsg.setMsg("验证码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 验证码
		boolean flag = validSmsCode(callbacktel, code);
		if (!flag) {
			resultMsg.setMsg("短信验证码错误");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		Map<String, String> param = new HashMap<>();
		param.put("productid", productid);
		param.put("callbacktel", callbacktel);
		if (callbackService.findUserCall(param) != null) {
			resultMsg.setMsg("您已经预约过这块地了");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTCallback entity = new TrwTCallback();
		entity.setFaciid(agentid);
		entity.setFaciid(faciid);
		entity.setProductid(productid);
		entity.setApplytime(new Date());
		entity.setCallname(callname);
		entity.setCallbacktel(callbacktel);
		if (callbackService.insert(entity)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(entity);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("免费通话失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/findCallback")
	@ApiOperation(httpMethod = "POST", value = "查找预约回电", notes = "查找预约回电")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "callstat", value = "回电状态(01未处理，02已回电)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "提交时间第一个框", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "提交时间第二个框", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callbacktel", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:applytime(提交时间)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> findCallback(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		TrwTAgent agent = ShiroKit.getUser();
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTCallback> page = new PageFactory<TrwTCallback>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		param.put("faciid", agent.getFaciid());
		// 判断当前登录是否是服务中心的管理员
		if (!ShiroKit.hasRole(Constant.ROLE_ADMIN)) {
			param.put("agentid", agent.getId());
		}
		List<Map<String, Object>> listCallback = callbackService.findCallback(param, page);
		map.put("listCallback", listCallback);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateCallback")
	@ApiOperation(httpMethod = "POST", value = "修改预约回电", notes = "修改预约回电")
	public ResultMsg<TrwTCallback> updateCallback(@RequestBody TrwTCallback callback) {
		ResultMsg<TrwTCallback> resultMsg = new ResultMsg<TrwTCallback>();
		TrwTAgent agent = ShiroKit.getUser();
		callback.setOperator(agent.getId());
		callback.setOperatorTime(LocalDateTime.now());
		if (!callbackService.updateById(callback)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(callback);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateCall")
	@ApiOperation(httpMethod = "POST", value = "修改预约回电", notes = "修改预约回电")
	@ApiImplicitParams({ @ApiImplicitParam(name = "callid", value = "预约回电id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callname", value = "预约回电名字", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callbacktel", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "callstat", value = "回电状态(01未处理，02已回电)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "备注", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTCallback> updateCall(HttpServletRequest req) {
		ResultMsg<TrwTCallback> resultMsg = new ResultMsg<TrwTCallback>();
		TrwTCallback callback = new TrwTCallback();
		callback.setCallid(req.getParameter("callid"));
		callback.setCallbacktel(req.getParameter("callbacktel"));
		callback.setCallstat(req.getParameter("callstat"));
		callback.setNote(req.getParameter("note"));
		callback.setCallname(req.getParameter("callname"));
		TrwTAgent agent = ShiroKit.getUser();
		callback.setOperator(agent.getId());
		callback.setOperatorTime(LocalDateTime.now());
		if (!callbackService.updateById(callback)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(callback);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getCallbackById")
	@ApiOperation(httpMethod = "POST", value = "查找一个预约回电详情", notes = "查找一个预约回电详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "callid", value = "预约回电id", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTCallback> getCallbackById(HttpServletRequest req) {
		ResultMsg<TrwTCallback> resultMsg = new ResultMsg<TrwTCallback>();
		String callid = req.getParameter("callid");
		TrwTCallback callBack = callbackService.selectById(callid);
		resultMsg.setData(callBack);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}
}
