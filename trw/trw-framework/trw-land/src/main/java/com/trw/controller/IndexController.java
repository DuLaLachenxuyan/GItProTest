package com.trw.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.trw.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.FaciFeignApi;
import com.trw.feign.NewsFeignApi;
import com.trw.model.TNews;
import com.trw.model.TrwTBimg;
import com.trw.model.TrwTLand;
import com.trw.service.IBimgService;
import com.trw.service.ILandService;
import com.trw.vo.CodeSunVo;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class IndexController extends BaseController {
	@Autowired
	private ILandService landService;
	@Autowired
	private FaciFeignApi faciFeignApi;
	@Autowired
	private IBimgService bimgService;
	@Autowired
	private NewsFeignApi newsFeignApi;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/main", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "首页", notes = "首页")
	@ApiImplicitParams({ @ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "排序", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recommendAreas", value = "区域/01为pc端,02为web端", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> main(HttpServletRequest req) {
		String recommendAreas = req.getParameter("recommendAreas");
		ResultMsg<Map<String, Object>> msg = new ResultMsg<Map<String, Object>>();
		Map<String, Object> param = new HashMap<>();
		String areaid = req.getParameter("areaid");
		param.put("areaid", areaid);
		Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
		// 土地信息
		param.put("currentTime", LocalDate.now());
		if ("02".equals(recommendAreas)) {
			param.put("recommendPosition", "04");// 推荐位置，04为web端首页推荐
		}
		if ("01".equals(recommendAreas)) {
			param.put("recommendPosition", "01");// 推荐位置，01为pc端首页推荐
		}
		param.put("recommendAreas", recommendAreas);// 推荐区域，02为web端，01为pc端
		String order = req.getParameter("order");
		if (StrKit.isBlank(order)) {
			param.put("order", "desc");
		}
		List<TrwTLand> lands = landService.selectIndexLand(page, param);
		// 加盟商信息
		Map<String, Object> facis = null;

		List<TNews> news = null;
		List<TNews> newsImage = null;
		try {
			facis = faciFeignApi.selectFaciList(areaid).getData(); // 加盟商信息
			ResultMsg<List<TNews>> re = newsFeignApi.selectMainNews(); // 新闻资讯信息
			news = re.getData();
			newsImage = null;
			if (news != null && news.size() > 3) {
				newsImage = news.subList(3, news.size());
				news = news.subList(0, 3);
			}
		} catch (Exception e) {
			logger.error("**********************" + e.getMessage());
		}

		// 轮播图片查询
		List<TrwTBimg> banlist = bimgService.selectBanner();
		// 获取分类码数据
		List<CodeSunVo> cateLandTp = (List<CodeSunVo>) redisService.get(Constant.DICPIX + "landType"); // 土地类型
		List<CodeSunVo> cateLandPlace = (List<CodeSunVo>) redisService.get(Constant.DICPIX + "landPlace");// 土地面积
		List<CodeSunVo> cateNewsNote = (List<CodeSunVo>) redisService.get(Constant.DICPIX + "newsNote");// 新闻资讯标签

		Map<String, Object> data = new HashMap<>();

		data.put("landTp", cateLandTp);
		data.put("landPlace", cateLandPlace);
		data.put("newsNote", cateNewsNote);
		data.put("lands", lands);
		data.put("facis", facis);
		data.put("news", news);
		data.put("newsImage", newsImage);
		data.put("banner", banlist);

		msg.setCode(Constant.CODE_SUCC);
		msg.setData(data);
		return msg;
	}

	// h5端
	/**
	 * 
	 * @Title: indexRecommendLands
	 * @Description: h5端首页推荐土地
	 * @author haochen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<Map<String,Object>> 返回类型
	 * @throws @date
	 *             2018年8月24日 下午3:12:19
	 */
	@RequestMapping(value = "/indexRecommendLands", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "首页推荐的土地", notes = "首页推荐的土地")
	@ApiImplicitParams({ @ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "排序", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recommendAreas", value = "区域/01为pc端,02为web端", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> indexRecommendLands(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> msg = new ResultMsg<Map<String, Object>>();
		Map<String, Object> param = new HashMap<>();
		String areaid = req.getParameter("areaid");
		String recommendAreas = req.getParameter("recommendAreas");
		param.put("areaid", areaid);
		// 土地信息
		param.put("currentTime", LocalDate.now());
		if ("02".equals(recommendAreas)) {
			param.put("recommendPosition", "04");// 推荐位置，04为web端首页推荐
		}
		if ("01".equals(recommendAreas)) {
			param.put("recommendPosition", "01");// 推荐位置，01为pc端首页推荐
		}
		param.put("recommendAreas", recommendAreas);// 推荐区域，02为web端，01为pc端
		String order = req.getParameter("order");
		if (StrKit.isBlank(order)) {
			param.put("order", "desc");
		}
		Page<TrwTLand> page = new PageFactory<TrwTLand>().defaultPage();
		List<TrwTLand> lands = landService.selectIndexLand(page, param);
		Map<String, Object> map = new HashMap<>();
		map.put("lands", lands);
		map.put("page", page);
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}

	/**
	 * 
	 * @Title: indexFacis
	 * @Description: h5端的加盟商
	 * @author haochen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<Map<String,Object>> 返回类型
	 * @throws @date
	 *             2018年8月24日 下午3:22:04
	 */
	@RequestMapping(value = "/indexFacis", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "首页加盟商", notes = "首页加盟商")
	@ApiImplicitParams({ @ApiImplicitParam(name = "areaid", value = "区域id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"), })
	public ResultMsg<Map<String, Object>> indexFacis(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> msg = new ResultMsg<Map<String, Object>>();
		String areaid = req.getParameter("areaid");
		Map<String, Object> facis = null;
		try {
			facis = faciFeignApi.selectFaciList(areaid).getData();// 加盟商信息
		} catch (Exception e) {
			logger.error("**********************" + e.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("facis", facis);
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}

	/**
	 * 
	 * @Title: indexNews
	 * @Description: 首页新闻咨询信息
	 * @author haochen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<Map<String,Object>> 返回类型
	 * @throws @date
	 *             2018年8月24日 下午3:29:39
	 */
	@RequestMapping(value = "/indexNews", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "首页新闻资讯信息", notes = "首页新闻资讯信息")
	public ResultMsg<Map<String, Object>> indexNews(HttpServletRequest req) {
		ResultMsg<Map<String, Object>> msg = new ResultMsg<Map<String, Object>>();
		List<TNews> news = null;
		List<TNews> newsImage = null;
		try {
			ResultMsg<List<TNews>> re = newsFeignApi.selectMainNews(); // 新闻资讯信息
			news = re.getData();
			newsImage = null;
			if (news != null && news.size() > 3) {
				newsImage = news.subList(3, news.size());
				news = news.subList(0, 3);
			}
		} catch (Exception e) {
			logger.error("**********************" + e.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("news", news);
		map.put("newsImage", newsImage);
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}
}
