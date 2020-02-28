package com.trw.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.trw.model.TrwTDeal;
import com.trw.model.TrwTLand;
import com.trw.service.IDealService;
import com.trw.util.DateUtil;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.DealDetail;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 交易前端控制器
 * </p>
 *
 * @author haochen
 * @since 2018-06-30
 */
@RestController
public class DealController extends BaseController {

	// @Autowired
	// private FaciFeignApi faciFeignApi;

	@Autowired
	private IDealService dealService;
	@Autowired
	private LandFeignApi landFeignApi;
	@Autowired
	private CustomerFeignApi customerFeignApi;

	@RequestMapping(value = "/auth/addDeal")
	@ApiOperation(httpMethod = "POST", value = "添加交易", notes = "添加交易")
	@ApiImplicitParams({})
	public ResultMsg<Object> addDeal(@RequestBody @Validated TrwTDeal deal, BindingResult result) throws Exception {
		ResultMsg<Object> resultMsg = new ResultMsg<Object>();
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				resultMsg.setCode(Constant.CODE_FAIL);
				resultMsg.setMsg(error.getDefaultMessage());
				return resultMsg;
			}
		}
		TrwTAgent agent = ShiroKit.getUser();
		// 查询土地
		ResultMsg<TrwTLand> landMsg = landFeignApi.getLandById(deal.getProductid());
		if (landMsg.getData() == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地的信息不存在");
			return resultMsg;
		}
		if (StrKit.isBlank(landMsg.getData().getAgentId())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地未分配经纪人,请先分配经纪人");
			return resultMsg;
		}
		//土地状态
		if (!StrKit.equals(landMsg.getData().getLandState(), Constant.CLIENT_PAYING)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该土地不是待交易状态,不能生成交易");
			return resultMsg;
		}
		// 客户需求
		ResultMsg<LandClient> landClient = customerFeignApi.getClientById(deal.getNeedid());
		if (landClient.getData() == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("客户需求不存在");
			return resultMsg;
		}
		if (StrKit.isBlank(landClient.getData().getAgentid())) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该客户需求未分配经纪人,请先分配经纪人");
			return resultMsg;
		}
		//需求土地状态
		if (!StrKit.equals(landClient.getData().getLandState(), Constant.CLIENT_PAYING)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("该客户需求不是待交易状态,不能生成交易");
			return resultMsg;
		}
		if(!StrKit.equals(landMsg.getData().getAgentId(),landClient.getData().getAgentid())){
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("客源的经纪人和地源的经纪人不一致,不能生成交易");
			return resultMsg;
		}
		deal.setAname(agent.getAname());
		deal.setCreatetime(LocalDateTime.now());
		deal.setAgentid(landMsg.getData().getAgentId());
		deal.setDealid(ToolUtil.generateId("TDl"));
		deal.setOperator(agent.getId());
		dealService.insertDeal(deal);
		resultMsg.setMsg("录入信息成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(deal);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getAgentDeal")
	@ApiOperation(httpMethod = "POST", value = "经纪人交易查询", notes = "经纪人交易查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealstat", value = "交易类别(01已签约,02交易已备案,03买家已确认,04交易完成,05交易终止)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "aname", value = "经纪人名字", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onetime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "twotime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:dealtime-交易时间, createtime-创建时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序),(默认为desc)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getAgentDeal(HttpServletRequest req) throws Exception {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		TrwTAgent agent = ShiroKit.getUser();
		Map<String, String> param = getParamMapFromRequest(req);
		param.put("agentid", agent.getId());
		Page<TrwTDeal> page = new PageFactory<TrwTDeal>().defaultPage();
		List<TrwTDeal> listAgentDeal = dealService.findDeal(param, page);
		Map<String, Object> map = new HashMap<>();
		map.put("listAgentDeal", listAgentDeal);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getFaciDeal")
	@ApiOperation(httpMethod = "POST", value = "服务商交易查询", notes = "服务商交易查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealstat", value = "交易类别(01已签约,02交易已备案,03买家已确认,04交易完成,05交易终止)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "agentid", value = "经纪人id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "onetime", value = "第一个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "twotime", value = "第二个时间框的时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:dealtime-交易时间, createtime-创建时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序),(默认为desc)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getFaciDeal(HttpServletRequest req) throws Exception {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		Map<String, String> param = getParamMapFromRequest(req);
		Page<TrwTDeal> page = new PageFactory<TrwTDeal>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		TrwTAgent agent = ShiroKit.getUser();
		param.put("faciid", agent.getFaciid());
		// 判断当前角色是否为经纪人管理员
	    if(!ShiroKit.hasRole(Constant.ROLE_ADMIN)){
	    	param.put("agentid", agent.getId());
	    }
	    //查询交易列表
		List<TrwTDeal> listFaciDeal = dealService.findDeal(param, page);
		map.put("listFaciDeal", listFaciDeal);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(map);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/getDetail")
	@ApiOperation(httpMethod = "POST", value = "交易详情", notes = "交易详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "dealid", value = "交易id", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTDeal> getDetail(HttpServletRequest req) throws Exception {
		ResultMsg<TrwTDeal> resultMsg = new ResultMsg<TrwTDeal>();
		String dealid = req.getParameter("dealid");
		DealDetail detailDeal = dealService.getDetail(dealid);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(detailDeal);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateDeal")
	@ApiOperation(httpMethod = "POST", value = "更新交易", notes = "更新交易")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "交易id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealstat", value = "交易类别(01已签约,02交易已备案,03买家已确认,04交易完成,05交易终止)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealtime", value = "达成交易时间(格式:yyyy-MM-dd)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "seller", value = "卖家名字", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sellID", value = "卖家身份证号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "selladdress", value = "卖家地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sellbank", value = "卖家开户银行", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sellbanknum", value = "卖家开户银行卡号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sellbankbranch", value = "卖家支行名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "selltel", value = "卖家电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buyer", value = "买家", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buytel", value = "买家电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buyID", value = "买家身份证号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buyaddress", value = "买家地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buybank", value = "买家开户银行", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buybanknum", value = "买家开户银行卡号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buybankbranch", value = "买家支行名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "productname", value = "地源详情", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "location", value = "所在地区", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "address", value = "详细地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sellfile", value = "卖家附件文件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "buyfile", value = "买家附件文件", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "aname", value = "经纪人名字", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dealprice", value = "价格", dataType = "string", paramType = "query") })
	public ResultMsg<TrwTDeal> updateDeal(HttpServletRequest req) throws Exception {
		ResultMsg<TrwTDeal> resultMsg = new ResultMsg<TrwTDeal>();
		TrwTAgent agent = ShiroKit.getUser();
		String id = req.getParameter("id");
		String dealtime = req.getParameter("dealtime");
		String dealprice = req.getParameter("dealprice");
		TrwTDeal entity = new TrwTDeal();
		if (!StrKit.isBlank(dealtime)) {
			if (!DateUtil.isValidDate(dealtime, "yyyy-MM-dd")) {
				resultMsg.setMsg("交易日期格式错误");
				resultMsg.setCode(Constant.CODE_FAIL);
				return resultMsg;
			}
			entity.setDealtime(LocalDate.parse(dealtime, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		if (StrKit.isNotEmpty(dealprice)) {
			entity.setDealprice(new BigDecimal(dealprice));
		}
		entity.setSelltel(req.getParameter("selltel"));
		entity.setId(Integer.valueOf(id));
		entity.setDealstat(req.getParameter("dealstat"));
		entity.setSeller(req.getParameter("seller"));
		entity.setSellID(req.getParameter("sellID"));
		entity.setSelladdress(req.getParameter("selladdress"));
		entity.setSellbank(req.getParameter("sellbank"));
		entity.setSellbanknum(req.getParameter("sellbanknum"));
		entity.setSellbankbranch(req.getParameter("sellbankbranch"));
		entity.setBuyer(req.getParameter("buyer"));
		entity.setBuyID(req.getParameter("buyID"));
		entity.setBuytel(req.getParameter("buytel"));
		entity.setBuyaddress(req.getParameter("buyaddress"));
		entity.setBuybank(req.getParameter("buybank"));
		entity.setBuybanknum(req.getParameter("buybanknum"));
		entity.setBuybankbranch(req.getParameter("buybankbranch"));
		entity.setProductname(req.getParameter("productname"));
		entity.setLocation(req.getParameter("location"));
		entity.setAddress(req.getParameter("address"));
		entity.setSellfile(req.getParameter("sellfile"));
		entity.setBuyfile(req.getParameter("buyfile"));
		entity.setAgentid(agent.getId());
		entity.setAname(agent.getAname());
		entity.setOperator(agent.getId());
		entity.setOperatorTime(LocalDateTime.now());
		if (dealService.updateById(entity)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(entity);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}

}
