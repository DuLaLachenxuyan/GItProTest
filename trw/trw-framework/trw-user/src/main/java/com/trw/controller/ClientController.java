package com.trw.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.MsgFeignApi;
import com.trw.manage.ShiroKit;
import com.trw.model.LandClient;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTNeedagent;
import com.trw.model.TrwTUser;
import com.trw.service.IAgentService;
import com.trw.service.IFaciService;
import com.trw.service.ILandClientService;
import com.trw.service.INeedagentService;
import com.trw.service.IUserService;
import com.trw.util.BeanUtil;
import com.trw.util.BusinessUtil;
import com.trw.util.PCAConvert;
import com.trw.util.ToolUtil;
import com.trw.vo.ClientVo;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author linhai123
 * @since 2018-06-27
 */
@RestController
public class ClientController extends BaseController {
	@Autowired
	private ILandClientService clientService;
	@Autowired
	private IFaciService faciService;
	@Autowired
	private IAgentService agentService;
	@Autowired
	private MsgFeignApi msgFeignApi;
	@Autowired
	private INeedagentService needagentService;
	@Autowired
	private IUserService iUserService;

	@RequestMapping(value = "/guest/postClient")
	@ApiOperation(httpMethod = "POST", value = "发布寻找土地需求", notes = "发布土地")
	@ApiImplicitParams({ @ApiImplicitParam(name = "location", value = "所在区域", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purpose", value = "土地用途", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transMode", value = "流转方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minAcreage", value = "土地最小面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxAcreage", value = "土地最大面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rentyear", value = "流转年限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mtitle", value = "信息标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "补充说明", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landContact", value = "土地联系人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minPrice", value = "土地最小价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxPrice", value = "土地最大价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "valiCode", value = "验证码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商ID", dataType = "string", paramType = "query") })
	public ResultMsg<LandClient> postClient(HttpServletRequest req) {
		ResultMsg<LandClient> resultMsg = new ResultMsg<>();
		Map<String, String> param = getParamMapFromRequest(req);
		String location = param.get("location");
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		TrwTFaci faci = faciService.getFaciByLoc(location);
		// 校验验证码
		LandClient landclient = null;

		landclient = BeanUtil.mapToObject(param, LandClient.class);
		// 发布时间
		landclient.setReporttime(LocalDateTime.now());
		landclient.setNeedid(idGenerate.generateSeq(Constant.PREFIXLCP));
		if (faci != null) {
			landclient.setFaciid(faci.getFaciid());
		}
		// 设置默认更新时间为当前
		landclient.setUpdatetime(LocalDateTime.now());
		// 发布人
		landclient.setReporter(tokenData.getUserId());
		// 设置客户来源,默认为发布需求客户
		landclient.setPsource(Constant.YES);
		// 设置客户交易状态,默认为等待审核
		landclient.setLandState(Constant.CLIENT_APPROVE);
		// 设置电话核实状态,默认为未核实
		landclient.setPhoneCheck(Constant.NO);
		landclient.setIsDel(Constant.YES);
		Boolean flg = clientService.insert(landclient);
		try {
			if (flg) { // 成功向服务上发送短信
				// 通过区域获取服务中心发送通知
				if (ToolUtil.isEmpty(faci)) {
					// 给平台发信息通知
					faci = BusinessUtil.getPlatFaci();
				}
				JSONObject smsJson = new JSONObject();
				smsJson.put("name", faci.getContacts());
				smsJson.put("region", PCAConvert.me().convert(location));
				smsJson.put("name2", param.get("landContact"));
				smsJson.put("tel", param.get("phone"));
				smsJson.put("demand", Constant.REQUIREMSG);
				String template = smsJson.toJSONString();
				// 处理消息
				String postMsg = redisService.getString(Constant.CONFIGPIX + "postMsg");
				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(postMsg);
				msg.setPhone(faci.getMobile());
				msg.setParam(template);
				msg.setUserId(faci.getFaciid());
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);
			}
		} catch (Exception e) {
			// 发布成功但是信息发送失败
			e.printStackTrace();
			resultMsg.setMsg(e.getMessage());
			resultMsg.setCode(Constant.CODE_SUCC);
			return resultMsg;
		}
		resultMsg.setMsg("发布成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/getLandClient")
	@ApiOperation(httpMethod = "GET", value = "找地客户查询", notes = "我的需求")
	@ApiImplicitParams({ @ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purpose", value = "土地用途", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transMode", value = "流转方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landLabel", value = "土地标签", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landState", value = "交易状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landPlaceMax", value = "土地面积最大值", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landPlaceMin", value = "土地面积最小值", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rentYearMin", value = "流转年限最小值", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rentYearMax", value = "流转年限最大值", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:reporttime-发布时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getLandClient(HttpServletRequest req) {
		Map<String, String> param = getParamMapFromRequest(req);
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		List<LandClient> list = clientService.selectLandClient(page, param);
		ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", page.getTotal());
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}

	@RequestMapping("/guest/getDemand")
	@ApiOperation(httpMethod = "POST", value = "个人中心-获取发布的需求", notes = "我的需求")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "transMode", value = "流转类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landState", value = "土地状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "查询开始时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "查询结束时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getDemand(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		if (tokenData == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		String userId = tokenData.getUserId();
		Map<String, String> param = getParamMapFromRequest(req);
		param.put("userId", userId);
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		// 根据条件查询
		List<LandClient> list = clientService.selectByTerm(page, param);
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("page", page);
		resultMsg.setData(result);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getMyClient")
	@ApiOperation(httpMethod = "POST", value = "服务中心-客源管理", notes = "服务中心-客源管理")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "landContact", value = "客户名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "联系电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "angentId", value = "经纪人", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ingrade", value = "意向等级(1强烈2一般)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "需求土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minTime", value = "最小查询时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxTime", value = "最大查询时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minAcreage", value = "最小查询面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxAcreage", value = "最大查询面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "psource", value = "客户来源(1发布需求2自有客户)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mtitle", value = "信息标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", required = true, paramType = "query") })
	public ResultMsg<Map<String, Object>> getMyClient(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		Map<String, String> param = getParamMapFromRequest(req);
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		TrwTAgent agent = ShiroKit.getUser();
		// 获取到登陆的服务平台id
		String faciid = agent.getFaciid();
		param.put("faciid", faciid);
		List<ClientVo> clients = clientService.selectClient(page, param);
		Map<String, Object> data = new HashMap<>();
		data.put("clients", clients);
		data.put("page", page);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(data);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/addClient")
	@ApiOperation(httpMethod = "POST", value = "服务中心-添加客户", notes = "手动添加客户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "landContact", value = "客户姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "需求地区", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transMode", value = "需求类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minAcreage", value = "最小面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxAcreage", value = "最大面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rentyear", value = "流转年限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minPrice", value = "土地最小价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxPrice", value = "土地最大价格", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注(补充说明)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "mtitle", value = "信息标题", dataType = "string", paramType = "query") })
	public ResultMsg<String> addClient(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		Map<String, String> param = getParamMapFromRequest(req);
		// TokenData tokenData = getTokenData();
		TrwTAgent agent = ShiroKit.getUser();
		LandClient client = BeanUtil.mapToObject(param, LandClient.class);
		client.setAgentid(agent.getId());
		client.setLandContact(req.getParameter("landContact"));
		client.setFaciid(req.getParameter("faciid"));
		client.setUpdatetime(LocalDateTime.now());
		client.setReporttime(LocalDateTime.now());
		client.setReporter(agent.getId());
		client.setNeedid(idGenerate.generateSeq(Constant.PREFIXLCP));
		client.setPsource("02"); // 客户来源为自有客户
		client.setIngrade("02"); // 意向等级默认02
		client.setPhoneCheck(Constant.YES); // 默认电话已核实
		client.setLandState(Constant.CLIENT_PAYING); // 交易状态为未交易
		client.setIsDel(Constant.YES);//删除状态为未删除
		client.setMtitle(req.getParameter("mtitle"));
		client.setOperator(agent.getId());
		client.setOperatorTime(LocalDateTime.now());
		boolean flg = clientService.insert(client);
		if (!flg) {
			resultMsg.setMsg("添加失败!");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		resultMsg.setMsg("添加成功!");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/selectMyClient")
	@ApiOperation(httpMethod = "GET", value = "服务中心-客源管理-查询需求详情", notes = "服务中心-查看需求")
	@ApiImplicitParams({ @ApiImplicitParam(name = "needid", value = "需求ID", dataType = "string", paramType = "query") })
	public ResultMsg<LandClient> selectMyClient(HttpServletRequest req) {
		String needid = req.getParameter("needid");
		LandClient selectMyClient = clientService.selectById(needid);
		ResultMsg<LandClient> resultMsg = new ResultMsg<>();
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(selectMyClient);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/mCupdateTime")
	@ApiOperation(httpMethod = "POST", value = "服务中心-客源管理-更新时间", notes = "服务中心-客源管理-更新时间")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "needid", value = "需求ID", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> mLupdateTime(HttpServletRequest req) {
		TrwTAgent user = ShiroKit.getUser();
		LandClient trwTClient = new LandClient();
		trwTClient.setNeedid(req.getParameter("needid"));
		trwTClient.setUpdatetime(LocalDateTime.now());
		trwTClient.setOperator(user.getId()); // 设置操作人
		trwTClient.setOperatorTime(LocalDateTime.now());
		clientService.updateById(trwTClient);
		ResultMsg<String> resultMsg = new ResultMsg<>();
		resultMsg.setMsg("刷新时间成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/guest/deleteMyClient")
	@ApiOperation(httpMethod = "POST", value = "个人中心-删除需求", notes = "个人中心-删除需求")
	@ApiImplicitParams({ @ApiImplicitParam(name = "needid", value = "需求ID", dataType = "string", paramType = "query") })
	public ResultMsg<String> deleteMyClient(HttpServletRequest req) {
		TokenData tokenData = getTokenData();
		LandClient trwTClient = new LandClient();
		trwTClient.setNeedid(req.getParameter("needid"));
		trwTClient.setIsDel(Constant.NO);
		trwTClient.setOperator(tokenData.getUserId()); // 设置操作人
		trwTClient.setOperatorTime(LocalDateTime.now());
		boolean flg = clientService.updateById(trwTClient);
		ResultMsg<String> resultMsg = new ResultMsg<>();
		if (flg) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setMsg("删除成功");
		} else {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("删除失败");
		}
		return resultMsg;
	}

	@RequestMapping(value = "/auth/allocClient")
	@ApiOperation(httpMethod = "POST", value = "服务中心-客户分配经纪人", notes = "客户分配经纪人")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "needid", value = "需求id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "angentId", value = "经纪人id", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> allocLand(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		Map<String, String> param = getParamMapFromRequest(req);
		LandClient need = clientService.selectById(param.get("needid"));
		if (need == null) {
			resultMsg.setMsg("需求不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTAgent angentId = agentService.selectById(param.get("angentId"));
		if (angentId == null) {
			resultMsg.setMsg("经纪人不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		TrwTAgent agent = ShiroKit.getUser();
		TrwTNeedagent entity = new TrwTNeedagent();
		entity.setAgentId(param.get("angentId"));
		String needid = param.get("needid");
		entity.setNeedid(needid);
		entity.setOperator(agent.getId());
		entity.setOperatorTime(LocalDateTime.now());
		entity.setId(ToolUtil.generateId("NAT"));
		boolean flg = needagentService.saveAgent(entity);
		if (flg) {
			try {
				TrwTFaci trwTFaci = faciService.selectById(angentId.getFaciid());
				if (trwTFaci == null) {
					trwTFaci = BusinessUtil.getPlatFaci();
				}
				// 给经纪人发送短信
				JSONObject smsJsonone = new JSONObject();
				smsJsonone.put("name", angentId.getAname());
				smsJsonone.put("region", trwTFaci.getAddress());
				smsJsonone.put("name2", trwTFaci.getContacts());
				smsJsonone.put("tel", trwTFaci.getMobile());
				String templateone = smsJsonone.toJSONString();
				// 处理消息
				String lookMsg = redisService.getString(Constant.CONFIGPIX + "lookMsg");
				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(lookMsg);
				msg.setPhone(angentId.getAphone());
				msg.setParam(templateone);
				msg.setUserId(param.get("angentId"));
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);

				// 给客户发送短信
				JSONObject smsJson = new JSONObject();
				if ("01".equals(need.getPsource())) {
					TrwTUser trwTUser = iUserService.getUser(need.getReporter());
					smsJson.put("name", trwTUser.getName());
				} else {
					smsJson.put("name", need.getLandContact());
				}
				smsJson.put("product", need.getMtitle());
				smsJson.put("name2", angentId.getAname());
				smsJson.put("tel", angentId.getAphone());
				String template = smsJson.toJSONString();
				// 处理消息
				String noticeUserMsg = redisService.getString(Constant.CONFIGPIX + "noticeUserMsg");

				msg.setMsgId(noticeUserMsg);
				msg.setPhone(need.getPhone());
				msg.setParam(template);
				msg.setUserId(need.getReporter());
				msg.setNeedSMS(Constant.YES);
				msgFeignApi.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg.setMsg(e.getMessage());
				return resultMsg;
			}
		}
		// 给需求表设置经纪人id
		if (flg) {
			resultMsg.setMsg("分配经纪人成功");
			resultMsg.setCode(Constant.CODE_SUCC);
		}
		return resultMsg;
	}

	@ApiOperation(httpMethod = "POST", value = "首页关键字搜索土地", notes = "首页关键字搜索土地需求")
	@RequestMapping(value = "/selectLandClient")
	@ApiImplicitParams({ @ApiImplicitParam(name = "areaid", value = "区域", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "text", value = "关键字", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> selectLandClient(HttpServletRequest req) {
		Map<String, String> param = getParamMapFromRequest(req);
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		List<LandClient> landClient = clientService.selectLandClient(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("landClient", landClient);
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;

	}

	@RequestMapping(value = "/getLandClientDetail")
	@ApiOperation(httpMethod = "POST", value = "土地需求详情", notes = "土地需求详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "needid", value = "土地需求id", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getLandClientDetail(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		String needid = req.getParameter("needid");
		LandClient landClientDetail = clientService.selectById(needid);
		 // 平台电话
        String adminPhone = redisService.getString(Constant.CONFIGPIX + "admin_phone");
		landClientDetail.setPhone(adminPhone);
		String agentid = landClientDetail.getAgentid();
		TrwTAgent agent = agentService.selectById(agentid);
		Map<String, String> map = new HashMap<>();
		map.put("landType", landClientDetail.getLandType());
		map.put("location", landClientDetail.getLocation());
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		List<LandClient> similarLandClient = clientService.selectSimilar(page, map);
		Map<String, Object> data = new HashMap<>();
		data.put("landClientDetail", landClientDetail);
		data.put("similarLandClient", similarLandClient);
		data.put("total", page.getTotal());
		data.put("agent", agent);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(data);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateMyClient")
	@ApiOperation(httpMethod = "POST", value = "服务中心-客源管理-编辑", notes = "服务中心-客源管理-编辑")
	@ApiImplicitParams({ @ApiImplicitParam(name = "needid", value = "客源id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landContact", value = "客户名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "土地需求地区", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ingrade", value = "意向登记", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "purpose", value = "土地用途", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "transMode", value = "流转方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minAcreage", value = "土地需求最小面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxAcreage", value = "土地需求最大面积", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rentyear", value = "流转年限", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "minPrice", value = "最小交易费用", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maxPrice", value = "最大交易费用", dataType = "string", paramType = "query") })
	public ResultMsg<String> updateMyClient(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		TrwTAgent user = ShiroKit.getUser();
		LandClient client = new LandClient();
		client.setNeedid(req.getParameter("needid"));
		client.setLandContact(req.getParameter("landContact"));
		client.setLocation(req.getParameter("location"));
		client.setPhone(req.getParameter("phone"));
		client.setIngrade(req.getParameter("ingrade"));
		client.setLandType(req.getParameter("landType"));
		client.setPurpose(req.getParameter("purpose"));
		client.setTransMode(req.getParameter("transMode"));
		client.setOperator(user.getId()); // 设置操作人
		client.setOperatorTime(LocalDateTime.now());
		String minAcreage = req.getParameter("minAcreage");
		String maxAcreage = req.getParameter("maxAcreage");
		if(minAcreage != null && maxAcreage != null ) {
			client.setMinAcreage(Float.parseFloat(minAcreage));
			client.setMaxAcreage(Float.parseFloat(maxAcreage));
		}
		String parameter = req.getParameter("rentyear");
		String parameter2 = req.getParameter("minPrice");
		String parameter3 = req.getParameter("maxPrice");
		if (parameter!=null && parameter2!=null && parameter3!=null) {
			BigDecimal rentyear = new BigDecimal(parameter);
			client.setRentyear(rentyear);
			BigDecimal minPrice = new BigDecimal(parameter2);
			client.setMinPrice(minPrice);
			BigDecimal maxPrice = new BigDecimal(parameter3);
			client.setMaxPrice(maxPrice);
		}
		clientService.updateById(client);
		resultMsg.setMsg("修改成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/getClientById", method = RequestMethod.POST)
	public ResultMsg<LandClient> getClientById(@RequestParam("needid") String needid) {
		ResultMsg<LandClient> rmg = new ResultMsg<>();
		LandClient client = clientService.selectById(needid);
		rmg.setData(client);
		return rmg;
	}

	/**
	 * 根据土地id查询需求的客户
	 *
	 * @param productid
	 * @return
	 */

	@RequestMapping(value = "/selectMatched", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<List<LandClient>> selectMatched(@RequestParam("productid") String productid) {
		ResultMsg<List<LandClient>> resultMsg = new ResultMsg<>();
		List<LandClient> landMatched = clientService.selectMatched(productid);
		resultMsg.setMsg("查询成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(landMatched);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/selectFaciClient")
	@ApiOperation(httpMethod = "POST", value = "查询服务商下面的客户", notes = "查询服务商下面的客户")
	@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query")
	public ResultMsg<List<LandClient>> selectFaciClient(HttpServletRequest req) {
		List<LandClient> selectFaciClient = clientService.selectTrwClient(req.getParameter("faciid"));
		ResultMsg<List<LandClient>> resultMsg = new ResultMsg<>();
		resultMsg.setData(selectFaciClient);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setMsg("查询成功");
		return resultMsg;
	}

	/**
	 * 
	 * @Title: updateClientById
	 * @Description: 根据ID修改客源信息 (模块的调用)
	 * @author haochen
	 * @param @param
	 *            landClient
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<LandClient> 返回类型
	 * @throws @date
	 *             2018年8月18日 下午4:35:25
	 */
	@RequestMapping(value = "/updateClientById")
	@ApiOperation(httpMethod = "POST", value = "根据ID修改需求信息", notes = "根据ID修改需求信息")
	public ResultMsg<LandClient> updateClientById(@RequestBody LandClient landClient) {
		ResultMsg<LandClient> resultMsg = new ResultMsg<>();
		TrwTAgent user = ShiroKit.getUser();
		landClient.setOperator(user.getId());
		landClient.setOperatorTime(LocalDateTime.now());
		if (clientService.updateById(landClient)) {
			resultMsg.setData(landClient);
			resultMsg.setCode(Constant.CODE_SUCC);
			return resultMsg;
		}
		resultMsg.setData(null);
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getLandClientByFaciId")
	@ApiOperation(httpMethod = "POST", value = "根据服务商id查询客源", notes = "根据服务商id查询客源")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query"), })
	public ResultMsg<List<LandClient>> getLandClientByFaciId(HttpServletRequest req) {
		ResultMsg<List<LandClient>> resultMsg = new ResultMsg<>();
		Map<String, String> param = getParamMapFromRequest(req);
		TrwTAgent agent = ShiroKit.getUser();
		if (!ShiroKit.hasRole(Constant.ROLE_ADMIN)) {
			param.put("agentId", agent.getId());
		}
		Page<LandClient> page = new PageFactory<LandClient>().defaultPage();
		List<LandClient> landClientList = clientService.getLandClientByFaciId(page, param);
		resultMsg.setData(landClientList);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	/**
	 * 
	 * @Title: updateCheckStat
	 * @Description: 修改审核状态
	 * @author gongzhen
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年8月27日
	 */
	@RequestMapping(value = "/auth/updateCheckStat")
	@ApiOperation(httpMethod = "POST", value = "服务中心客源管理核实状态", notes = "服务中心客源管理核实状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "needid", value = "需求ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phoneCheck", value = "电话核实", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landState", value = "客源状态", dataType = "string", paramType = "query") })
	public ResultMsg<String> updateCheckStat(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		TrwTAgent user = ShiroKit.getUser();
		LandClient client = clientService.selectById(req.getParameter("needid"));
		client.setPhoneCheck(req.getParameter("phoneCheck"));
		client.setLandState(req.getParameter("landState"));
		client.setOperator(user.getId());
		client.setOperatorTime(LocalDateTime.now());
		if(clientService.updateById(client)) {
			resultMsg.setMsg("核实状态成功!");
			resultMsg.setCode(Constant.CODE_SUCC);
			return resultMsg;
		}
		resultMsg.setMsg("核实状态失败!");
		resultMsg.setCode(Constant.CODE_FAIL);
		return null;

	}
}
