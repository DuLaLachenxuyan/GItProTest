package com.trw.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.trw.manage.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.trw.constant.Constant;
import com.trw.feign.AgentFeignApi;
import com.trw.feign.MsgFeignApi;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.service.IAllocationService;
import com.trw.service.ILandService;
import com.trw.util.BusinessUtil;
import com.trw.util.ToolUtil;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 16:47 2018/7/7
 * @Modified By:
 */
@RestController
public class AllcationController extends BaseController {
	@Autowired
	private IAllocationService iAllocationService;
	@Autowired
	private MsgFeignApi msgFeignApi;
	@Autowired
	private ILandService landService;
	@Autowired
	private AgentFeignApi agentFeignApi;

	@RequestMapping(value = "/saveAllocal")
	@ApiOperation(httpMethod = "POST", value = "分配经纪人", notes = "分配经纪人")
	public Boolean saveAllocal(@RequestBody TrwTAllocation entity) {
		return iAllocationService.saveAllocal(entity);
	}

	@RequestMapping(value = "/getAllocation")
	@ApiOperation(httpMethod = "POST", value = "根据土地id查看经纪人", notes = "根据土地id查看经纪人")
	public ResultMsg<TrwTAllocation> getAllocation(@RequestParam("productid") String productid) {
		ResultMsg<TrwTAllocation> resultMsg = new ResultMsg<>();
		Wrapper<TrwTAllocation> wrapper = new EntityWrapper<TrwTAllocation>().eq("productid", productid);
		TrwTAllocation Allocation = iAllocationService.selectOne(wrapper);
		resultMsg.setData(Allocation);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/saveAllAllotAgent")
	@ApiOperation(httpMethod = "POST", value = "批量分配经纪人", notes = "批量分配经纪人")
	public Boolean saveAllAllotAgent(@RequestParam("landsID") String landsID,
			@RequestParam("angentID") String angentID) {
		List<TrwTAllocation> allocations = new ArrayList<>();
		TrwTAgent agent = ShiroKit.getUser();
		List<TrwTLand> lands = new ArrayList<>();
		String[] landsid = landsID.split(",");
		for (int i = 0; i < landsid.length; i++) {
			TrwTAllocation allocation = new TrwTAllocation();
			TrwTLand trwTLand = new TrwTLand();
			allocation.setId(ToolUtil.generateId("All"));
			allocation.setProductid(landsid[i]);
			allocation.setAgentId(angentID);
			allocation.setOperator(agent.getId());
			allocation.setOperatorTime(LocalDateTime.now());
			trwTLand.setProductid(landsid[i]);
			trwTLand.setAgentId(angentID);
			trwTLand.setAgencySituation(Constant.NO);//02为已经代理
			allocations.add(allocation);
			lands.add(trwTLand);
		}
		boolean flg = iAllocationService.saveAllAllotAgent(allocations, lands);
		return flg;
	}

	@RequestMapping(value = "/auth/updateAllocLand")
	@ApiOperation(httpMethod = "POST", value = "服务中心-土地分配经纪人", notes = "服务中心-土地分配经纪人")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "landId", value = "土地id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "angentId", value = "经纪人id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "severtoken", value = "severtoken", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> updateAllocLand(HttpServletRequest req) {
		Map<String, String> param = getParamMapFromRequest(req);
		ResultMsg<String> resultMsg = new ResultMsg<>();
		TrwTLand land = landService.getLandInfo(param.get("landId"));
		if (land == null) {
			resultMsg.setMsg("土地不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		ResultMsg<TrwTAgent> angentMsg = agentFeignApi.getAgentById(param.get("angentId"));
		TrwTAgent angent = angentMsg.getData();
		if (angent == null) {
			resultMsg.setMsg("经纪人不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTAgent agent = ShiroKit.getUser();

		TrwTAllocation entity = new TrwTAllocation();
		entity.setAgentId(req.getParameter("angentId"));
		entity.setProductid(req.getParameter("landId"));
		entity.setOperator(agent.getId());
		entity.setOperatorTime(LocalDateTime.now());
		entity.setId(ToolUtil.generateId("ALL"));
		try {
			if (iAllocationService.saveAllocal(entity)) {
				TrwTFaci trwTFaci = (TrwTFaci) redisService.get(Constant.FACI + angent.getFaciid());
				if (trwTFaci == null) {
					trwTFaci = BusinessUtil.getPlatFaci();
				}
				// 给经纪人发送短信
				JSONObject smsJsonone = new JSONObject();
				smsJsonone.put("name", angent.getAname());
				smsJsonone.put("region", trwTFaci.getAddress());
				smsJsonone.put("name2", trwTFaci.getContacts());
				smsJsonone.put("tel", trwTFaci.getMobile());
				String templateone = smsJsonone.toJSONString();
				// 处理消息
				String lookMsg = redisService.getString(Constant.CONFIGPIX + "lookMsg");

				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(lookMsg);
				msg.setPhone(angent.getAphone());
				msg.setParam(templateone);
				msg.setUserId(param.get("angentId"));
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);

				// 给用户发送短信
				JSONObject smsJson = new JSONObject();
				smsJson.put("name", land.getLandContact());
				smsJson.put("product", land.getProductname());
				smsJson.put("name2", angent.getAname());
				smsJson.put("tel", angent.getAphone());
				String template = smsJson.toJSONString();
				// 处理消息
				String noticeUserMsg = redisService.getString(Constant.CONFIGPIX + "noticeUserMsg");

				msg.setMsgId(noticeUserMsg);
				msg.setPhone(land.getPhone());
				msg.setParam(template);
				msg.setUserId(land.getReporter());
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		resultMsg.setMsg("分配经纪人成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateAllAllotAgent")
	@ApiOperation(httpMethod = "POST", value = "服务中心-批量分配经纪人", notes = "服务中心-批量分配经纪人")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "landsID", value = "多个土地id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "angentID", value = "经纪人id", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> updateAllAllotAgent(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		String landsID = req.getParameter("landsID");
		String angentID = req.getParameter("angentID");
		ResultMsg<TrwTAgent> angentId = agentFeignApi.getAgentById(angentID);
		TrwTAgent trwTAgent = angentId.getData();
		if (trwTAgent == null) {
			resultMsg.setMsg("经纪人不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		Boolean flg = this.saveAllAllotAgent(landsID, angentID);
		try {
			if (flg) {
				TrwTFaci trwTFaci = (TrwTFaci) redisService.get(Constant.FACI + trwTAgent.getFaciid());
				if (trwTFaci == null) {
					trwTFaci = BusinessUtil.getPlatFaci();
				}
				String[] landid = landsID.split(",");
				for (int i = 0; i < landid.length; i++) {
					TrwTLand trwTLand = landService.selectById(landid[i]);
					// 给经纪人发送短信
					JSONObject smsJsonone = new JSONObject();
					smsJsonone.put("name", trwTAgent.getAname());
					smsJsonone.put("region", trwTFaci.getAddress());
					smsJsonone.put("name2", trwTFaci.getContacts());
					smsJsonone.put("tel", trwTFaci.getMobile());
					String templateone = smsJsonone.toJSONString();
					// 处理消息
					String lookMsg = redisService.getString(Constant.CONFIGPIX + "lookMsg");
					NoticeMsg msg = new NoticeMsg();
					msg.setMsgId(lookMsg);
					msg.setPhone(trwTAgent.getAphone());
					msg.setParam(templateone);
					msg.setUserId(angentID);
					msg.setNeedSMS(Constant.YES);
					msgFeignApi.sendMessage(msg);

					// 给用户发送短信
					JSONObject smsJson = new JSONObject();
					smsJson.put("name", trwTLand.getLandContact());
					smsJson.put("product", trwTLand.getProductname());
					smsJson.put("name2", trwTAgent.getAname());
					smsJson.put("tel", trwTAgent.getAphone());
					String template = smsJson.toJSONString();
					// 处理消息
					String noticeUserMsg = redisService.getString(Constant.CONFIGPIX + "noticeUserMsg");

					msg.setMsgId(noticeUserMsg);
					msg.setPhone(trwTLand.getPhone());
					msg.setParam(template);
					msg.setUserId(trwTLand.getReporter());
					msg.setNeedSMS(Constant.YES);
					msgFeignApi.sendMessage(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		resultMsg.setMsg("批量分配经纪人成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}
}
