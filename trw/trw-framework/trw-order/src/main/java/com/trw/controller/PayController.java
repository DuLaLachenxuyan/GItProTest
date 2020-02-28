package com.trw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.trw.constant.Constant;
import com.trw.feign.FaciFeignApi;
import com.trw.feign.LandFeignApi;
import com.trw.feign.MsgFeignApi;
import com.trw.feign.UserFeignApi;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTOrder;
import com.trw.model.TrwTUser;
import com.trw.service.IOrderService;
import com.trw.util.AlipayConfig;
import com.trw.util.PCAConvert;
import com.trw.util.RenderUtil;
import com.trw.util.StrKit;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
* @ClassName: PayController 
* @Description: 支付控制器
* @author luojing
* @date 2018年7月4日 下午3:40:29 
*
 */
@Controller
public class PayController extends BaseController {
	
	@Value("${trw.paysuccess}")
	private String paysuccess;
	@Value("${trw.payfail}")
	private String payfail;
	
	@Autowired
	private IOrderService orederService;
	@Autowired
	private LandFeignApi landFeignApi;
	@Autowired
	private MsgFeignApi msgFeignApi;
	@Autowired
	private FaciFeignApi faciFeignApi;
	@Autowired
	private UserFeignApi userFeignApi;

	@RequestMapping(value = "/guest/pay")
	@ApiOperation(httpMethod = "POST", value = "支付宝支付", notes = "预约看地")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "productName", value = "订单名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fee", value = "付款金额", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "需求备注", dataType = "string", paramType = "query") })
	public void pay(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> param = super.getParamMapFromRequest(request);
		ResultMsg<String> resultMsg = new ResultMsg<>();
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String orderId = param.get("orderId");

		if (StrKit.isBlank(orderId)) {
			resultMsg.setMsg("订单id不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			// return resultMsg;
			RenderUtil.renderJson(response,resultMsg);
			return;
		}
		// 订单名称，必填
		String subject = param.get("productName");
		if (StrKit.isBlank(subject)) {
			resultMsg.setMsg("订单名称");
			resultMsg.setCode(Constant.CODE_FAIL);
			RenderUtil.renderJson(response,resultMsg);
			return;
		}
		// 付款金额，必填
		String total_amount = param.get("fee");
		if (StrKit.isBlank(total_amount)) {
			resultMsg.setMsg("付款金额");
			resultMsg.setCode(Constant.CODE_FAIL);
			RenderUtil.renderJson(response,resultMsg);
			return;
		}
		// 商品描述，可空
		String body = param.get("remark");
		// 超时时间 可空
		String timeout_express = "2m";
		// 销售产品码 必填
		String product_code = "FAST_INSTANT_TRADE_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
				AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
				AlipayConfig.SIGNTYPE);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(orderId);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(paysuccess);
		// 设置同步地址
		alipay_request.setReturnUrl(payfail);

		// form表单生产
		String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/payresult")
	@ApiOperation(httpMethod = "POST", value = "支付完成", notes = "支付完成")
	public String payresult(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
		
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		String out_trade_no = request.getParameter("out_trade_no");
		// 支付宝交易号
		// 交易状态
		String trade_status = request.getParameter("trade_status");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		// 计算得出通知验证结果
		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,"RSA2");

		if (verify_result) {// 验证成功
			// 请在这里加上商户的业务逻辑程序代码
			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if ("TRADE_FINISHED".equals(trade_status)) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				// 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
			} else if ("TRADE_SUCCESS".equals(trade_status)) {
				// 判断该笔订单是否在商户网站中已经做过处理
				TrwTOrder order =orederService.selectById(out_trade_no);
				
				if(order!=null) {
					if(!Constant.ORDER_PAYING.equals(order.getOrderstat())) { //订单状态错误
						 return "redirect:"+payfail;
					}
					//订单处理
					orederService.updateOrder(order,params);
					//短信通知服务中心 
					TrwTFaci faciInfo = faciFeignApi.getFaciByfaciId(order.getFaciid()).getData();
					TrwTLand landInfo = landFeignApi.getLandById(order.getProductid()).getData();
					TrwTUser userInfo = userFeignApi.getUser(order.getUserId()).getData();
					
					String lookMsg =redisService.getString(Constant.CONFIGPIX+"lookMsg");
					JSONObject smsJson = new JSONObject();
					smsJson.put("name", faciInfo.getContacts());
					smsJson.put("region", PCAConvert.me().convert(landInfo.getLocation()));
					smsJson.put("name2", userInfo.getName());
					smsJson.put("tel", userInfo.getPhone());
					
					String template =smsJson.toJSONString();
					//处理消息
		            NoticeMsg msg = new NoticeMsg();
					msg.setMsgId(lookMsg);
					msg.setPhone(faciInfo.getMobile());
					msg.setParam(template);
					msg.setUserId(order.getUserId());
					msg.setNeedSMS(Constant.YES);
					msgFeignApi.sendMessage(msg); 
//					smssrvice.sendNotic(faciInfo.getMobile(),lookMsg,smsJson);
				}
				
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				// 如果有做过处理，不执行商户的业务程序
				
				// 注意：
				// 如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
			}else {
				return "redirect:"+paysuccess;
			}
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
		} else {// 验证失败
			System.out.println("fail");
		}
		return "redirect:"+paysuccess;
	}
	
	
//	@RequestMapping(value = "/generateSeq")
//	@ApiOperation(httpMethod = "POST", value = "测试生成主键", notes = "测试生成主键")
//	@ResponseBody
//	@ApiImplicitParams({ 
//		@ApiImplicitParam(name = "prefix", value = "前缀", dataType = "string", paramType = "query") 
//	})
//	public String generateSeq(HttpServletRequest req) {
//		String prefix = req.getParameter("prefix");
//		String id = null;
//		if(!StrKit.isBlank(prefix)) {
//			id =idGenerate.generateSeq(prefix);
//		}
//		return id;
//	}
	

}
