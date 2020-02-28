package com.trw.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.CustomerFeignApi;
import com.trw.model.LandClient;
import com.trw.model.PlatTEnshrine;
import com.trw.service.IEnshrineService;
import com.trw.util.StrKit;
import com.trw.vo.Enshrine;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 收藏信息
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
@RestController
public class EnshrineController extends BaseController {

	@Autowired
	private IEnshrineService platTEnshrineService;
	@Autowired
	private CustomerFeignApi customerFeignApi;

    @RequestMapping(value = "/guest/getPTS", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "查询收藏信息", notes = "查询收藏信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "landType", value = "土地类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "landState", value = "土地状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "type=1为土地，type=2为新闻,type=3为需求土地", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "oneTime", value = "第一个时间框的时间", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "twoTime", value = "第二个时间框的时间", dataType = "String", paramType = "query") })
	public ResultMsg<Map<String, Object>> getPTS(HttpServletRequest req) throws ParseException {
		Map<String, Object> map = new HashMap<>();
		Page<PlatTEnshrine> page = new PageFactory<PlatTEnshrine>().defaultPage();
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		// 判断是否登录
		if (tokenData == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 判断type和userid是否为空
		String type = req.getParameter("type");
		if (StrKit.isEmpty(type)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		String oneTime = req.getParameter("oneTime");
		String twoTime = req.getParameter("twoTime");
		String landType = req.getParameter("landType");
		String landState = req.getParameter("landState");
		if (Constant.ENSHRINE_LAND.equals(type)) {// 查询出对应用户的收藏土地信息
			map.put("ehrtype", Constant.ENSHRINE_LAND);
			map.put("userid", tokenData.getUserId());
			map.put("oneTime", oneTime);
			map.put("twoTime", twoTime);
			map.put("landType", landType);
			map.put("landState", landState);
			List<Enshrine> list = platTEnshrineService.selectPTS(page, map);
			Map<String, Object> parm = new HashMap<>();
			parm.put("list", list);
			parm.put("total", page.getTotal());
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(parm);
			return resultMsg;
		}else if (Constant.ENSHRINE_NEWS.equals(type)) { //查询出对应用户的收藏新闻资讯信息
			// map.put("ehrtype", "2");
			// map.put("userid", tokenData.getUserId());
			// map.put("oneTime", oneTime);
			// map.put("twoTime", twoTime);
			// List<PlatTEnshrine> listone =
			// platTEnshrineService.selectPTS(page, map);
			// resultMsg.setCode(Constant.CODE_SUCC);
			// resultMsg.setData(listone);
			// return resultMsg;
		}else if (Constant.Enshrine_CLIENT.equals(type)) {// 查询出对应用户的收藏需求/找地信息
			Map<String, String> param = getParamMapFromRequest(req);
			param.put("userid",getTokenData().getUserId());
			List<Map<String, Object>> landClientList =platTEnshrineService.findLandClient(page,param);
			map.put("landClientList", landClientList);
			map.put("total", page.getTotal());
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(map);
			return resultMsg;
		}
		return resultMsg;
	}

    @RequestMapping(value = "/guest/addPTS")
	@ApiOperation(httpMethod = "POST", value = "添加收藏", notes = "添加收藏")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "type=1:土地,type=2:新闻,type=3:需求", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "type=1：土地id,type=2:新闻id,type=2:需求id", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "productName", value = "收藏名称", dataType = "String", paramType = "query")
	})
	public ResultMsg<String> addPTS(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<>();
		PlatTEnshrine platTEnshrine = new PlatTEnshrine();
		String type = req.getParameter("type");
		String id = req.getParameter("id");
		String productName = req.getParameter("productName");
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		// 判断是否登录
		if (tokenData == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(type)) {
			resultMsg.setMsg("类型不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(id)) {
			resultMsg.setMsg("类型对应的id不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 查询出土地的名称，并把对应的信息插入到收藏表当中
		if (Constant.ENSHRINE_LAND.equals(type)) {
			Map<String, Object> map = new HashMap<>();
			map.put("userid", tokenData.getUserId());
			map.put("landid", id);
			
			if(StrKit.isBlank(productName)) {
				platTEnshrineService.deleteByMap(map);
				resultMsg.setMsg("取消收藏");
				return resultMsg;
			}
			
			List<PlatTEnshrine> platTEnshrines = platTEnshrineService.selectByMap(map);
			if (null == platTEnshrines || platTEnshrines.size() == 0) {
				platTEnshrine.setCtime(new Date());
				platTEnshrine.setLandid(id);
				platTEnshrine.setCtypes(productName);
				platTEnshrine.setEhrtype(Constant.ENSHRINE_LAND);
				platTEnshrine.setUserid(tokenData.getUserId());
				this.platTEnshrineService.insert(platTEnshrine);
				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setMsg("添加收藏");
				return resultMsg;
			} else {
				platTEnshrineService.deleteByMap(map);
				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setMsg("取消收藏");
				return resultMsg;
			}
		}
		// 如果type是2则为新闻，查询出新闻资讯的名称，并把对应的信息插入到收藏表当中
		if (Constant.ENSHRINE_NEWS.equals(type)) {
			// TNews tNews=itNewsService.selectById(id);
			// platTEnshrine.setCtime(new Date());
			// platTEnshrine.setCtid(id);
			// platTEnshrine.setCtypes(tNews.getTitle());
			// platTEnshrine.setEhrtype("2");
			// platTEnshrine.setUserid(tokenData.getUserId());
			// this.platTEnshrineService.insert(platTEnshrine);
			// resultMsg.setMsg("添加成功");
			// resultMsg.setCode(Constant.CODE_SUCC);
			// return resultMsg;
		}
		// 需求土地收藏
		if (Constant.Enshrine_CLIENT.equals(type)) {
			Map<String, Object> map = new HashMap<>();
			map.put("userid", tokenData.getUserId());
			map.put("landid", id);// 就存landid这个字段,根据类型来判断即可
			List<PlatTEnshrine> platTEnshrines = platTEnshrineService.selectByMap(map);
			if (null == platTEnshrines || platTEnshrines.size() == 0) {
				ResultMsg<LandClient> landClient = customerFeignApi.getClientById(id);
				if (landClient.getData() == null) {
					resultMsg.setCode(Constant.CODE_FAIL);
					resultMsg.setMsg("对应的土地需求信息不存在");
					return resultMsg;
				}
				platTEnshrine.setCtime(new Date());
				platTEnshrine.setLandid(id);
				platTEnshrine.setCtypes(landClient.getData().getMtitle());
				platTEnshrine.setEhrtype(Constant.Enshrine_CLIENT);
				platTEnshrine.setUserid(tokenData.getUserId());
				platTEnshrineService.insert(platTEnshrine);
				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setMsg("收藏成功");
				return resultMsg;
			}
			platTEnshrineService.deleteByMap(map);
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setMsg("已取消收藏");
			return resultMsg;
		}
		return resultMsg;
	}

    @RequestMapping(value = "/guest/deletePTS")
	@ApiOperation(httpMethod = "POST", value = "取消收藏", notes = "取消收藏")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ctid", value = "收藏ID", dataType = "string", paramType = "query") })
	public ResultMsg<String> deletePTS(HttpServletRequest req) {
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		ResultMsg<String> resultMsg = new ResultMsg<>();
		if (tokenData == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		this.platTEnshrineService.deleteById(Integer.valueOf(req.getParameter("ctid")));
		resultMsg.setMsg("删除成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

    @RequestMapping(value = "/guest/getCollectionLand")
	@ApiOperation(httpMethod = "POST", value = "查询是否收藏", notes = "查询是否收藏")
	@ApiImplicitParams({ @ApiImplicitParam(name = "landid", value = "土地id", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, String>> getCollectionLand(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		map.put("landid", req.getParameter("landid"));
		map.put("userid", tokenData.getUserId());
		List<PlatTEnshrine> platTEnshrines = platTEnshrineService.selectByMap(map);
		Map<String, String> parm = new HashMap<>();
		if (platTEnshrines == null || platTEnshrines.size() == 0) {
			parm.put("platTEnshrines", "收藏");
		} else {
			parm.put("platTEnshrines", "已收藏");
		}
		ResultMsg<Map<String, String>> resultMsg = new ResultMsg<>();
		resultMsg.setData(parm);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	/**
	 * 收藏土地次数
	 * 
	 * @param productId
	 * @return
	 */
    @RequestMapping(value = "/getEnshrineNum",method=RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<Integer> getEnshrineNum(@RequestParam("productId") String productId) {
		Wrapper<PlatTEnshrine> wrapper = new EntityWrapper<PlatTEnshrine>().eq("landid", productId);
		int enshrineNum = platTEnshrineService.selectCount(wrapper);
		ResultMsg<Integer> resultMsg = new ResultMsg<>();
		resultMsg.setMsg("查询成功！");
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(enshrineNum);
		return resultMsg;
	}

	@RequestMapping(value = "/guest/ifCollectionClientLand")
	@ApiOperation(httpMethod = "POST", value = "查询是否收藏土地的需求", notes = "查询是否收藏土地的需求")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "土地和需求id", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, String>> ifCollectionClientLand(HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		map.put("landid", req.getParameter("id"));
		map.put("userid", tokenData.getUserId());
		List<PlatTEnshrine> platTEnshrines = platTEnshrineService.selectByMap(map);
		Map<String, String> parm = new HashMap<>();
		if (platTEnshrines == null || platTEnshrines.size() == 0) {
			parm.put("platTEnshrines", "收藏");
		} else {
			parm.put("platTEnshrines", "已收藏");
		}
		ResultMsg<Map<String, String>> resultMsg = new ResultMsg<>();
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(parm);
		return resultMsg;
	}

}
