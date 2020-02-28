package com.trw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trw.mq.Producer;
import com.trw.service.impl.SmsService;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;
import com.trw.vo.ScoreMsg;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
public class MsgController {
	@Autowired
	private Producer producer;
	@Autowired
	private SmsService service;
	
//	@RequestMapping(value = "/sendMsg")
//	@ApiOperation(httpMethod = "POST", value = "短信发送", notes = "短信发送")
//	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "电话号码", dataType = "string", paramType = "query") })
//	public ResultMsg<String> sendMsg(HttpServletRequest req) {
//		String phone = req.getParameter("phone");
//		ResultMsg<String> msg = service.sendSmsCode(phone);
//		return msg;
//	}
	
	@RequestMapping(value = "/sendMsg" ,method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(httpMethod = "POST", value = "短信发送", notes = "短信发送")
	public ResultMsg<String> sendMsg(@ApiParam(value = "电话号码")  @RequestParam String phone) {
		ResultMsg<String> msg = service.sendSmsCode(phone);
		return msg;
	}
	
	@RequestMapping(value="/sendMessage",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "普通消息", notes = "普通消息")
	public void sendMessage(@ApiParam(value = "普通消息" ,required=true ) @RequestBody NoticeMsg msg) {
		producer.sendMsg(msg);
	}
	
	
	@RequestMapping(value = "/scoreMsg",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "积分消息", notes = "积分消息")
	public void scoreMsg(@ApiParam(value = "积分消息" ,required=true ) @RequestBody ScoreMsg msg) {
		producer.scoreMsg(msg);
	}
	
	

}
