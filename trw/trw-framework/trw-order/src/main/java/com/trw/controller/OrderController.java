package com.trw.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.AgentFeignApi;
import com.trw.feign.FaciFeignApi;
import com.trw.feign.LandFeignApi;
import com.trw.feign.MsgFeignApi;
import com.trw.feign.UserFeignApi;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTOrder;
import com.trw.model.TrwTUser;
import com.trw.service.IOrderService;
import com.trw.util.BusinessUtil;
import com.trw.util.DateUtil;
import com.trw.util.PCAConvert;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.CliOrder;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: OrderController
 * @Description: 用户订单
 * @author haochen
 * @date 2018年7月4日
 *
 */
@RestController
public class OrderController extends BaseController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private LandFeignApi landFeignApi;
	@Autowired
	private MsgFeignApi msgFeignApi;
	@Autowired
	private FaciFeignApi faciFeignApi;
	@Autowired
	private UserFeignApi userFeignApi;
	@Autowired
	private AgentFeignApi agentFeignApi;

	@RequestMapping(value = "/guest/appointment")
	@ApiOperation(httpMethod = "POST", value = "预约看地", notes = "预约看地")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "土地id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "appointdate", value = "预约时间(yyyy-MM-dd HH:mm:ss)", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fee", value = "带看费", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "num", value = "带看次数", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务中心id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "lookmodel", value = "看地模式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "需求备注", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTOrder> appointment(HttpServletRequest req) {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<TrwTOrder>();
		TrwTOrder entity = new TrwTOrder();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		String appointdate = req.getParameter("appointdate");
		String productid = req.getParameter("productid");
		String faciid = req.getParameter("faciid");
		String takemodel = req.getParameter("lookmodel");
		String num = req.getParameter("num");
		String fee = req.getParameter("fee");
		String remark = req.getParameter("remark");
		String userId = tokenData.getUserId();

		if (!DateUtil.isValidDate(appointdate, "yyyy-MM-dd HH:mm:ss")) {
			resultMsg.setMsg("预约日期格式错误");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 查看土地是否存在
		ResultMsg<TrwTLand> land = landFeignApi.getLandById(productid);
		if (land.getData() == null) {
			resultMsg.setMsg("土地不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 查看土地对应的经纪人
		if (StrKit.isBlank(land.getData().getAgentId())) {
			resultMsg.setMsg("土地对应的经纪人不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 查看这个人还有没有其他订单

		if (StrKit.isBlank(fee)) {
			fee = "0";
		}
		if (StrKit.isBlank(num)) {
			num = "0";
		}
		entity.setAppointment(DateUtil.parse(appointdate, "yyyy-MM-dd HH:mm:ss"));
		entity.setProductid(productid);
		entity.setAgentId(land.getData().getAgentId());
		entity.setOrderid(ToolUtil.generateId("TOD"));
		BigDecimal feeB = new BigDecimal(fee);
		entity.setFee(feeB);
		entity.setUserId(userId);
		entity.setOrdertime(new Date());
		entity.setFaciid(faciid);
		entity.setTakemodel(takemodel);
		entity.setRemark(remark);
		entity.setOrderstat(Constant.ORDER_PAYING);
		entity.setOrdertype(Constant.ORDER_TYPE_LOOK);
		entity.setNum(Integer.parseInt(num));
		//操作人
		entity.setOperator(getTokenData().getUserId());
		boolean flg = orderService.insert(entity);
		try {
			if (flg) { // 成功向服务上发送短信
				// 通过区域获取服务中心发送通知
				TrwTFaci trwTFacis = (TrwTFaci) redisService.get(Constant.FACI + faciid);
				ResultMsg<TrwTFaci> faciData = faciFeignApi.getFaciByLoc(trwTFacis.getLocation());
				TrwTFaci faci = faciData.getData();
				if (faci == null) {
					faci = BusinessUtil.getPlatFaci();
				}
				ResultMsg<TrwTUser> trwTUserResultMsg = userFeignApi.getUser(tokenData.getUserId());
				JSONObject smsJson = new JSONObject();
				smsJson.put("name", faci.getContacts());
				smsJson.put("region", PCAConvert.me().convert(trwTFacis.getLocation()));
				smsJson.put("name2", trwTUserResultMsg.getData().getName());
				smsJson.put("tel", trwTUserResultMsg.getData().getPhone());
				smsJson.put("project", land.getData().getProductname());
				String template = smsJson.toJSONString();
				// 处理消息
				String allocationMsg = redisService.getString(Constant.CONFIGPIX + "allocationMsg");
				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(allocationMsg);
				msg.setPhone(faci.getMobile());
				msg.setParam(template);
				msg.setUserId(faciid);
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);

				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setData(entity);
				return resultMsg;
			}
			resultMsg.setMsg("预约失败");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

	}

	@RequestMapping(value = "/guest/findOrder")
	@ApiOperation(httpMethod = "POST", value = "订单查询", notes = "订单查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ordertype", value = "订单分类(01带看订单,02土地订单)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:ordertime-订单时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query"), })
	public ResultMsg<Map<String, Object>> findOrder(HttpServletRequest req) throws Exception {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTOrder> page = new PageFactory<TrwTOrder>().defaultPage();
		param.put("userId", tokenData.getUserId());
		List<CliOrder> listOrd = orderService.findOrder(param, page);
		Map<String, Object> map = new HashMap<>();
		map.put("listOrd", listOrd);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/guest/findOrdDetail")
	@ApiOperation(httpMethod = "POST", value = "用户端查看订单详情", notes = "用户端查看订单详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderid", value = "订单id", dataType = "string", paramType = "query"), })
	public ResultMsg<CliOrder> findOrdDetail(HttpServletRequest req) {
		ResultMsg<CliOrder> resultMsg = new ResultMsg<CliOrder>();
		String orderid = req.getParameter("orderid");
		if (orderid == null) {
			resultMsg.setData(null);
			resultMsg.setMsg("订单id不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		CliOrder ordDetail = orderService.findOrdDetail(orderid);
		resultMsg.setData(ordDetail);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/guest/cancelOrder")
	@ApiOperation(httpMethod = "POST", value = "取消订单", notes = "取消订单")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderid", value = "订单id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderstat", value = "订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单)", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTOrder> cancelOrder(HttpServletRequest req) {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<TrwTOrder>();
		String orderid = req.getParameter("orderid");
		String orderstat = req.getParameter("orderstat");
		if (orderid == null) {
			resultMsg.setData(null);
			resultMsg.setMsg("订单id不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (orderstat == null) {
			resultMsg.setData(null);
			resultMsg.setMsg("订单状态不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTOrder entity = new TrwTOrder();
		entity.setOrderid(orderid);
		entity.setOrderstat(orderstat);
		entity.setOperator(getTokenData().getUserId());
		entity.setOperatorTime(LocalDateTime.now());
		Boolean flg = orderService.updateById(entity);
		try {
			if (flg) { // 成功向服务上发送短信
				// 通过区域获取服务中心发送通知
				TrwTOrder trwTOrder = orderService.selectById(orderid);
				TrwTFaci faci = (TrwTFaci) redisService.get(Constant.FACI + trwTOrder.getFaciid());
				if (StrKit.isBlank(trwTOrder.getFaciid())) {
					// 给平台发信息通知
					faci = BusinessUtil.getPlatFaci();
				}
				TrwTUser user = userFeignApi.getUser(getTokenData().getUserId()).getData();
				// 处理消息
				String cancel = redisService.getString(Constant.CONFIGPIX + "cancel");
				JSONObject smsJson = new JSONObject();
				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(cancel);
				if (StrKit.isBlank(trwTOrder.getAgentId())) {
					smsJson.put("name", faci.getContacts());
					smsJson.put("region", PCAConvert.me().convert(faci.getLocation()));
					smsJson.put("name2", user.getName());
					smsJson.put("tel", user.getPhone());
					String template = smsJson.toJSONString();
					msg.setPhone(faci.getMobile());
					msg.setParam(template);
					msg.setUserId(faci.getFaciid());
					msg.setNeedSMS(Constant.YES);
					msgFeignApi.sendMessage(msg);
					resultMsg.setCode(Constant.CODE_SUCC);
					resultMsg.setData(entity);
					return resultMsg;
				}
				TrwTAgent agent = agentFeignApi.getAgentById(trwTOrder.getAgentId()).getData();
				smsJson.put("name", agent.getAname());
				smsJson.put("region", PCAConvert.me().convert(faci.getLocation()));
				smsJson.put("name2", user.getName());
				smsJson.put("tel", user.getPhone());
				String template = smsJson.toJSONString();
				msg.setPhone(faci.getMobile());
				msg.setParam(template);
				msg.setUserId(agent.getId());
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);
				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setData(entity);
				return resultMsg;
			}
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("取消订单失败");
			resultMsg.setData(null);
			return resultMsg;
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
	}

	@RequestMapping(value = "/guest/addLandOrder")
	@ApiOperation(httpMethod = "POST", value = "卖家-土地订单", notes = "卖家-土地订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "土地编码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "charges", value = "佣金", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "需求备注", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTOrder> addLandOrder(HttpServletRequest req) {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<TrwTOrder>();
		Map<String, String> param = getParamMapFromRequest(req);
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		TrwTOrder trwTOrder = new TrwTOrder();
		trwTOrder.setUserId(tokenData.getUserId());
		trwTOrder.setFaciid(param.get("faciid"));
		if (param.get("charges") != null) {
			trwTOrder.setCharges(new BigDecimal(param.get("charges")));
		}
		trwTOrder.setRemark(param.get("remark"));
		trwTOrder.setProductid(param.get("productid"));
		trwTOrder.setOrderid(ToolUtil.generateId("TOD"));
		trwTOrder.setOrdertime(new Date());
		trwTOrder.setAppointment(trwTOrder.getOrdertime());
		// 类型+状态
		trwTOrder.setOrderstat(Constant.ORDER_PAYING);
		trwTOrder.setOrdertype("02");
		Boolean flag = orderService.insert(trwTOrder);
		if (flag) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(trwTOrder);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}
    /**
     * 
    * @Title: updateOrder 
    * @Description: 修改订单(模块调用)
    * @author haochen
    * @param @param order
    * @param @return  参数说明 
    * @return ResultMsg<TrwTOrder> 返回类型    
    * @throws 
    * @date 2018年8月20日 下午2:10:26
     */
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<TrwTOrder> updateOrder(@RequestBody TrwTOrder order) {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<>();
		if (orderService.updateById(order)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(order);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		return resultMsg;
	}

	@RequestMapping(value = "/guest/affirmOrder")
	@ApiOperation(httpMethod = "POST", value = "用户确认完成-订单", notes = "用户确认完成-订单")
	public ResultMsg<TrwTOrder> affirmOrder(@RequestBody TrwTOrder order) {
		ResultMsg<TrwTOrder> resultMsg = new ResultMsg<>();
		if (StrKit.isBlank(order.getAgentId())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("经纪人id不能为空");
			return resultMsg;
		}
		order.setOperator(getTokenData().getUserId());
		order.setOperatorTime(LocalDateTime.now());
		//事务不能放在try中
		orderService.updateOrderById(order);
		try {
			TrwTUser user = userFeignApi.getUser(getTokenData().getUserId()).getData();
			TrwTAgent agent = agentFeignApi.getAgentById(order.getAgentId()).getData();
			// 处理消息
			JSONObject smsJson = new JSONObject();
			smsJson.put("name", user.getName());
			smsJson.put("name2", agent.getAname());
			String template = smsJson.toJSONString();
			String orderOverMsg = redisService.getString(Constant.CONFIGPIX + "orderOverMsg");
			NoticeMsg msg = new NoticeMsg();
			msg.setMsgId(orderOverMsg);
			msg.setPhone(user.getPhone());
			msg.setParam(template);
			msg.setUserId(user.getUserid());
			msg.setNeedSMS(Constant.YES);
			msgFeignApi.sendMessage(msg);
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(order);
			return resultMsg;
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

	}
	
	/**
	 * 
	* @Title: updateAccBalance 
	* @Description: 用户账户余额支出
	* @author gongzhen
	* @param @param acc
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年8月13日
	 */
	@RequestMapping(value="/guest/payByBal",method=RequestMethod.POST)
	public ResultMsg<String> updateAccBalance(@RequestBody @Valid TrwTAccDetail acc, BindingResult result){
	  	  ResultMsg<String> resultMsg = new ResultMsg<>();
	  	  if (result.hasErrors()) {
	          for (ObjectError error : result.getAllErrors()) {
	              resultMsg.setCode(Constant.CODE_FAIL);
	              resultMsg.setMsg(error.getDefaultMessage());
	              return resultMsg;
	          }
	      }
		TrwTOrder order =orderService.selectById(acc.getOrderId());
		
		Map<String, String> params = new HashMap<>();
		params.put("buyer_logon_id", acc.getBuyerLogonId());
		params.put("body",acc.getBody());
		params.put("trade_no",acc.getTradeNo());
		params.put("total_amount",acc.getTotalAmount()+"");
		try {
			orderService.updateOrder(order,params);
			resultMsg.setCode(Constant.CODE_SUCC);
	        resultMsg.setMsg("支付成功");
		} catch (Exception e) {
			resultMsg.setCode(Constant.CODE_FAIL);
	        resultMsg.setMsg(e.getMessage());
		}
		return resultMsg;
	}
}
