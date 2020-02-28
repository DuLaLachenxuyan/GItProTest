package com.trw.controller;

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
import com.trw.manage.ShiroKit;
import com.trw.model.PlatTNotice;
import com.trw.service.IPlatTNoticeService;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 通知表 前端控制器
 * </p>
 *
 * @author haochen123
 * @since 2018-07-20
 */
@RestController
public class PlatTNoticeController extends BaseController {

	@Autowired
	private IPlatTNoticeService noticeService;

	@RequestMapping(value = "/auth/getNotices")
	@ApiOperation(httpMethod = "POST", value = "查询通知", notes = "查询通知")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "creater", value = "创建者id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "starttime", value = "时间第一个框", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "endtime", value = "时间第二个框", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:createtime(创建时间)", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> getNotices(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<Map<String, Object>>();
		Map<String, String> param = getParamMapFromRequest(req);
		Page<PlatTNotice> page = new PageFactory<PlatTNotice>().defaultPage();
		List<PlatTNotice> listNotices = noticeService.findNotices(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("listNotices", listNotices);
		map.put("pages", page.getPages());
		map.put("total", page.getTotal());
		resultMsg.setData(map);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/addNotice")
	@ApiOperation(httpMethod = "POST", value = "增加通知", notes = "增加通知")
	@ApiImplicitParams({ @ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"), })
	public ResultMsg<PlatTNotice> addNotice(HttpServletRequest req) {
		ResultMsg<PlatTNotice> resultMsg = new ResultMsg<PlatTNotice>();
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String type = req.getParameter("type");
		if (title == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("标题不能为空");
			resultMsg.setData(null);
			return resultMsg;
		}
		if (content == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("内容不能为空");
			resultMsg.setData(null);
			return resultMsg;
		}
		PlatTNotice notice = new PlatTNotice();
		if (type != null) {
			notice.setType(Integer.valueOf(type));
		}
		notice.setTitle(title);
		notice.setContent(content);
		notice.setCreater(ShiroKit.getUser().getId());
		notice.setCreatetime(new Date());
		if (noticeService.insert(notice)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(notice);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		resultMsg.setMsg("添加通知失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateNotice")
	@ApiOperation(httpMethod = "POST", value = "更新通知", notes = "更新通知")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "通知id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "title", value = "标题", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "content", value = "内容", dataType = "string", paramType = "query"), })
	public ResultMsg<PlatTNotice> updateNotice(HttpServletRequest req) {
		ResultMsg<PlatTNotice> resultMsg = new ResultMsg<PlatTNotice>();
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String type = req.getParameter("type");
		String strId = req.getParameter("id");
		if (title == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("标题不能为空");
			resultMsg.setData(null);
			return resultMsg;
		}
		if (content == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("内容不能为空");
			resultMsg.setData(null);
			return resultMsg;
		}
		if (strId == null) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("通知id不能为空");
			resultMsg.setData(null);
			return resultMsg;
		}
		PlatTNotice notice = new PlatTNotice();
		if (type != null) {
			notice.setType(Integer.valueOf(type));
		}
		notice.setId(Integer.valueOf(strId));
		notice.setTitle(title);
		notice.setContent(content);
		if (noticeService.updateById(notice)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(notice);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		resultMsg.setMsg("更新失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/delNotice")
	@ApiOperation(httpMethod = "POST", value = "删除通知", notes = "删除通知")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "通知id", dataType = "string", paramType = "query"), })
	public ResultMsg<PlatTNotice> delNotice(HttpServletRequest req) {
		ResultMsg<PlatTNotice> resultMsg = new ResultMsg<PlatTNotice>();
		String strId = req.getParameter("id");
		int id = Integer.valueOf(strId);
		if (noticeService.deleteById(id)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		resultMsg.setMsg("删除失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/findNotice")
	@ApiOperation(httpMethod = "POST", value = "查询通知详情", notes = "查询通知详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "通知id", dataType = "string", paramType = "query"), })
	public ResultMsg<PlatTNotice> findNotice(HttpServletRequest req) {
		ResultMsg<PlatTNotice> resultMsg = new ResultMsg<PlatTNotice>();
		String strId = req.getParameter("id");
		int id = Integer.valueOf(strId);
		PlatTNotice notice = noticeService.selectById(id);
		resultMsg.setData(notice);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

}
