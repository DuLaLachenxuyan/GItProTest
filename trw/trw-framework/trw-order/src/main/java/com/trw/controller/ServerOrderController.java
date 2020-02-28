package com.trw.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.CustomerFeignApi;
import com.trw.feign.LandFeignApi;
import com.trw.manage.ShiroKit;
import com.trw.model.LandClient;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTOrder;
import com.trw.service.ISerOrderService;
import com.trw.util.DateUtil;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import com.trw.vo.SerOrder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: ServerOrderController
 * @Description: 服务中心订单
 * @author haochen
 * @date 2018年6月22日
 *
 */
@RestController
public class ServerOrderController extends BaseController {

	@Autowired
	private ISerOrderService orderService;
	@Autowired
	private LandFeignApi landFeignApi;
	@Autowired
	private CustomerFeignApi customerFeignApi;

	@RequestMapping(value = "/auth/findFaciOrder")
	@ApiOperation(httpMethod = "POST", value = "服务中心-订单查询", notes = "服务中心-订单查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "agentId", value = "经纪人id", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "aname", value = "经纪人名字", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "ordertype", value = "订单类别(01带看订单,03添加生成)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态类(03带看完成,05取消订单,06带看中,07待确认)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:ordertime-订单时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> findFaciOrder(HttpServletRequest req) throws Exception {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTOrder> page = new PageFactory<TrwTOrder>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		TrwTAgent agent = ShiroKit.getUser();
		param.put("faciid", agent.getFaciid());
		// 判断当前角色是否为经纪人管理员
		if (!ShiroKit.hasRole(Constant.ROLE_ADMIN)) {
			param.put("agentId", agent.getId());
		}
		List<SerOrder> listFaciOrd = orderService.findServerOrder(param, page);
		map.put("listFaciOrd", listFaciOrd);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/findAgentOrder")
	@ApiOperation(httpMethod = "POST", value = "经纪人订单查询", notes = "经纪人订单查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "aname", value = "经纪人名字", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "ordertype", value = "订单类别(01带看订单,03添加生成)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态类(03带看完成,05取消订单,06带看中,07待确认)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:ordertime-订单时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> findAgentOrder(HttpServletRequest req) throws Exception {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		TrwTAgent agent = ShiroKit.getUser();
		Map<String, String> param = getParamMapFromRequest(req);
		param.put("agentId", agent.getId());
		Page<TrwTOrder> page = new PageFactory<TrwTOrder>().defaultPage();
		List<SerOrder> listAgentOrd = orderService.findServerOrder(param, page);
		Map<String, Object> map = new HashMap<>();
		map.put("listAgentOrd", listAgentOrd);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getFaciOrdDetail")
	@ApiOperation(httpMethod = "POST", value = "服务中心订单详情", notes = "服务中心订单详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderid", value = "订单id", dataType = "String", paramType = "query") })
	public ResultMsg<SerOrder> getFaciOrdDetail(HttpServletRequest req) throws Exception {
		ResultMsg<SerOrder> resultMsg = new ResultMsg<SerOrder>();
		Map<String, String> param = getParamMapFromRequest(req);
		SerOrder faciOrdDetail = orderService.findFaciOrdDetail(param);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(faciOrdDetail);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateFaciOrder")
	@ApiOperation(httpMethod = "POST", value = "服务中心-更新订单", notes = "服务中心-更新订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderid", value = "订单id", dataType = "String", required = true, paramType = "query"),
			@ApiImplicitParam(name = "ordertype", value = "订单类别(01带看订单,02添加生成)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态类(01待付款,02已支付,03带看完成,04已评价,05取消订单,06带看中,07待确认)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "appointment", value = "预约时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "takemodel", value = "看地模式(01-单次带看，02-多次带看，03-保证金带看)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "charges", value = "佣金", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fee", value = "带看费", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "需求备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "num", value = "带看次数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "agentid", value = "经纪人id", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTOrder> updateFaciOrder(HttpServletRequest req) throws ParseException {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<TrwTOrder>();
		TrwTAgent agent = ShiroKit.getUser();
		Map<String, String> param = getParamMapFromRequest(req);
		TrwTOrder order = new TrwTOrder();
		order.setOrderid(param.get("orderid"));
		order.setOrdertype(param.get("ordertype"));
		order.setOrderstat(param.get("orderstat"));
		order.setTakemodel(param.get("takemodel"));
		order.setOperator(agent.getId());
		order.setOperatorTime(LocalDateTime.now());
		if (param.get("appointment") != null) {
			if (!DateUtil.isValidDate(param.get("appointment"), "yyyy-MM-dd HH:mm:ss")) {
				resultMsg.setMsg("预约日期格式错误");
				resultMsg.setCode(Constant.CODE_FAIL);
				return resultMsg;
			}
			order.setAppointment(DateUtil.parse(param.get("appointment"), "yyyy-MM-dd HH:mm:ss"));
		}
		if (param.get("charges") != null) {
			order.setCharges(new BigDecimal(param.get("charges")));
		}
		if (param.get("fee") != null) {
			order.setFee(new BigDecimal(param.get("fee")));
		}
		if (param.get("num") != null) {
			order.setNum(Integer.valueOf(param.get("num")));
		}

		order.setRemark(param.get("remark"));
		order.setAgentId(param.get("agentid"));
		// 查看订单是否能修改
		TrwTOrder trwTOrder = orderService.selectById(param.get("orderid"));
		if (StrKit.equals(trwTOrder.getOrderstat(), Constant.ORDER_FINISH)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("已经完成的订单不能修改");
			return resultMsg;
		}
		if (StrKit.equals(trwTOrder.getOrderstat(), Constant.ORDER_EVAL)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("已经评价的订单不能修改");
			return resultMsg;
		}
		order.setAgentId(trwTOrder.getAgentId());
		orderService.updateFaciOrder(order);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(order);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/addCliOrder")
	@ApiOperation(httpMethod = "POST", value = "服务中心-生成带看-添加生成", notes = "服务中心-生成带看-添加生成")
	@ApiImplicitParams({ @ApiImplicitParam(name = "fee", value = "带看费", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "num", value = "带看次数", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "needid", value = "需求id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "appointment", value = "带看时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "productid", value = "土地id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTOrder> generateLook(HttpServletRequest req) throws ParseException {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<TrwTOrder>();
		TrwTAgent agent = ShiroKit.getUser();
		String appointment = req.getParameter("appointment");
		String needid = req.getParameter("needid");
		String productid = req.getParameter("productid");
		String num = req.getParameter("num");
		String fee = req.getParameter("fee");
		String orderstat = req.getParameter("orderstat");
		if (StrKit.isBlank(needid)) {
			resultMsg.setMsg("客户名字不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(orderstat)) {
			resultMsg.setMsg("订单状态不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(productid)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("土地product名字不能为空");
			return resultMsg;
		}
		if (StrKit.isBlank(appointment)) {
			resultMsg.setMsg("预约日期不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (!DateUtil.isValidDate(appointment, "yyyy-MM-dd")) {
			resultMsg.setMsg("预约日期格式错误");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		Date appointmentDate = DateUtil.parse(appointment, "yyyy-MM-dd");
		if (appointmentDate.before(new Date())) {
			resultMsg.setMsg("预约日期必须在今天之后");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(num)) {
			resultMsg.setMsg("带看次数不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(fee)) {
			resultMsg.setMsg("带看费不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 查询该土地对应的经纪人id
		ResultMsg<TrwTLand> landMsg = landFeignApi.getLandById(productid);
		if (landMsg.getData() == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地不存在");
			return resultMsg;
		}
		if (StrKit.isBlank(landMsg.getData().getAgentId())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地对应的经纪人名字为空,请先分配经纪人");
			return resultMsg;
		}
		// 判断土地是不是待交易状态
		if (!StrKit.equals(landMsg.getData().getLandState(), Constant.CLIENT_PAYING)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地不是待交易的状态,不能生成带看");
			return resultMsg;
		}
		// 设置客户需求
		ResultMsg<LandClient> landClient = customerFeignApi.getClientById(needid);
		if (landClient.getData() == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该客源需求信息不存在");
			return resultMsg;
		}
		if (StrKit.isBlank(landClient.getData().getAgentid())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("客源需求信息的经纪人不存在,请先分配经纪人");
			return resultMsg;
		}
		// 判断客源需求是不是待交易状态
		if (!StrKit.equals(landClient.getData().getLandState(), Constant.CLIENT_PAYING)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该客源需求不是待交易的状态,不能生成带看订单");
			return resultMsg;
		}
		if (!StrKit.equals(landMsg.getData().getAgentId(), landClient.getData().getAgentid())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("客源的经纪人和地源的经纪人不一致,不能生成带看订单");
			return resultMsg;
		}
		TrwTOrder trwTOrder = new TrwTOrder();
		trwTOrder.setAgentId(landMsg.getData().getAgentId());
		trwTOrder.setUserId(needid);
		trwTOrder.setAppointment(appointmentDate);
		trwTOrder.setFee(new BigDecimal(fee));
		trwTOrder.setOperator(agent.getId());
		int numInt = Integer.valueOf(num);
		trwTOrder.setNum(numInt);
		if (StrKit.equals(num, Constant.YES)) {
			trwTOrder.setTakemodel(Constant.YES);// 单次带看
		}
		trwTOrder.setTakemodel(Constant.NO);// 多次带看
		trwTOrder.setProductid(productid);
		trwTOrder.setOrdertype(Constant.ORDER_TYPE_ADDSPAN);// 添加生成订单
		trwTOrder.setFaciid(req.getParameter("faciid"));
		trwTOrder.setRemark(req.getParameter("remark"));
		trwTOrder.setOrderstat(orderstat); // 订单状态
		trwTOrder.setOrderid(ToolUtil.generateId("TOD"));
		trwTOrder.setOrdertime(new Date());
		orderService.insertServiceOrder(trwTOrder);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(trwTOrder);
		return resultMsg;
	}
}
