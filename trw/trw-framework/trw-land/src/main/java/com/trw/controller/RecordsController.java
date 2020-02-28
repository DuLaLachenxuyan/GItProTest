package com.trw.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.annotion.Permission;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.Records;
import com.trw.model.TrwTAgent;
import com.trw.service.RecordsService;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 跟进
 * </p>
 *
 * @author
 * @since 2018-06-30
 */
@RestController
public class RecordsController extends BaseController {
	@Autowired
	private RecordsService recordsService;

	@Permission
	@RequestMapping(value = "/auth/addRecords")
	@ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-添加跟进", notes = "服务中心-我的土地-添加跟进")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderid", value = "订单id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "productid", value = "土地ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "needid", value = "客户id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "aname", value = "客户名称", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "客户电话", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "text", value = "跟进内容", dataType = "string", paramType = "query") })
	public ResultMsg<Records> addRecords(HttpServletRequest req) {
		Records records = new Records();
		TrwTAgent agent = ShiroKit.getUser();
		records.setCtName(req.getParameter("aname"));
		records.setCtPhone(req.getParameter("phone"));
		records.setFollowid(ToolUtil.generateId("FOL"));
		String productid = req.getParameter("productid");
		String needid = req.getParameter("needid");
		String orderid = req.getParameter("orderid");
		if (StrKit.isNotEmpty(productid)) {
			records.setId(productid);
		}
		if (StrKit.isNotEmpty(needid)) {
			records.setId(needid);
		}
		if (StrKit.isNotEmpty(orderid)) {
			records.setId(orderid);
		}
		records.setText(req.getParameter("text"));
		records.setFollowTime(new Date());
		records.setFollowPerson(agent.getId());
		records.setOperatorTime(LocalDateTime.now());
		records.setOperator(agent.getId());
		ResultMsg<Records> resultMsg = new ResultMsg<>();
		if (recordsService.insert(records)) {
			resultMsg.setMsg("添加跟进成功");
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(records);
			return resultMsg;
		}
		resultMsg.setMsg("添加跟进失败");
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateRecords")
	@ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-编辑跟进", notes = "服务中心-我的土地-编辑跟进")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "followid", value = "跟进id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "text", value = "编辑内容", dataType = "string", paramType = "query") })
	public ResultMsg<Records> updateRecords(HttpServletRequest req) {
		TrwTAgent agent = ShiroKit.getUser();
		Records records = new Records();
		records.setFollowid(req.getParameter("followid"));
		records.setText(req.getParameter("text"));
		records.setOperator(agent.getId());
		records.setOperatorTime(LocalDateTime.now());
		ResultMsg<Records> resultMsg = new ResultMsg<>();
		if (recordsService.updateRecords(records)) {
			resultMsg.setMsg("编辑成功");
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(records);
			return resultMsg;
		}
		resultMsg.setMsg("编辑失败");
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/findLandRecords")
	@ApiOperation(httpMethod = "POST", value = "查询土地跟进记录", notes = "查询土地跟进记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "土地ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "needid", value = "客户ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "orderid", value = "订单ID", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> findLandRecords(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		Map<String, String> map = getParamMapFromRequest(req);
		Page<Records> page = new PageFactory<Records>().defaultPage();
		List<Records> records = recordsService.selectBylandid(page, map);
		Map<String, Object> param = new HashMap<>();
		param.put("records", records);
		param.put("page", page);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(param);
		return resultMsg;
	}

}
