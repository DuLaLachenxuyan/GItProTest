package com.trw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.mapper.CommentDao;
import com.trw.mapper.NewsDao;
import com.trw.mapper.NewsTypeDao;
import com.trw.model.Comment;
import com.trw.model.News;
import com.trw.model.NewsType;
import com.trw.model.TNews;
import com.trw.service.ITNewsService;
import com.trw.util.PageUtil;
import com.trw.util.StrKit;
import com.trw.vo.PageBean;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class NewsController extends BaseController{

	@Autowired
	private NewsTypeDao newsTypeDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private ITNewsService newsService;

	@RequestMapping("/list")
	@ApiOperation(httpMethod = "POST", value = "新闻列表", notes = "新闻列表")
	public String newsList(HttpServletRequest request) {
		String typeId = request.getParameter("typeId");
		String page = request.getParameter("page");
		if (StrKit.isEmpty(page)) {
			page = "1";
		}
		News s_news = new News();
		if (StrKit.isNotEmpty(typeId)) {
			s_news.setTypeId(Integer.parseInt(typeId));
		}
		int total = newsDao.newsCount(s_news, null, null);
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageUtil.pageSize);
		List<News> newestNewsListWithType = newsDao.newsListPage(s_news, pageBean, null, null);
		request.setAttribute("newestNewsListWithType", newestNewsListWithType);
		String typeName = newsTypeDao.getNewsTypeById(typeId).getTypeName();
		request.setAttribute("navCode", PageUtil.genNewsListNavigation(typeName, typeId));
		request.setAttribute("pageCode",
				PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), PageUtil.pageSize, typeId));
		request.setAttribute("mainPage", "news/newsList.jsp");
		request.setAttribute("title", typeName);
		return "foreground/newsTemp";
	}

	@RequestMapping("/show")
	@ApiOperation(httpMethod = "POST", value = "show列表", notes = "show列表")
	public String newsShow(HttpServletRequest request) {
		String newsId = request.getParameter("newsId");
		newsDao.newsClick(newsId);
		News news = newsDao.getNewsById(newsId);
		Comment s_comment = new Comment();
		s_comment.setNewsId(Integer.parseInt(newsId));
		List<Comment> commentList = commentDao.commentList(s_comment, null, null, null);
		request.setAttribute("commentList", commentList);

		List<News> newestNewsList = newsDao.newsList("isHead", 0, 5);
		request.setAttribute("newestNewsList", newestNewsList);

		List<News> hotNewsList = newsDao.newsList("isHot", 0, 5);
		request.setAttribute("hotNewsList", hotNewsList);

		request.setAttribute("news", news);
		request.setAttribute("pageCode", this.genUpAndDownPageCode(newsId));
		request.setAttribute("navCode",
				PageUtil.genNewsNavigation(news.getTypeName(), news.getTypeId() + "", news.getTitle()));
		request.setAttribute("title", news.getTitle());
		request.setAttribute("mainPage", "/foreground/news/newsShow.jsp");
		return "/foreground/newsTemp";
	}

	private String genUpAndDownPageCode(String newsId) {
		News upNews = newsDao.getUpPageId(newsId);
		News downNews = newsDao.getDownPageId(newsId);
		StringBuffer pageCode = new StringBuffer();
		if (upNews == null || upNews.getNewsId() == -1) {
			pageCode.append("<p>上一篇：没有了</p>");
		} else {
			pageCode.append("<p>上一篇：<a href='/news/news/show?newsId=" + upNews.getNewsId() + "'>" + upNews.getTitle()
					+ "</a></p>");
		}
		if (downNews == null || downNews.getNewsId() == -1) {
			pageCode.append("<p>下一篇：没有了</p>");
		} else {
			pageCode.append("<p>下一篇：<a href='/news/news/show?newsId=" + downNews.getNewsId() + "'>"
					+ downNews.getTitle() + "</a></p>");
		}
		return pageCode.toString();
	}

	@RequestMapping("/preSave")
	public String newsPreSave(HttpServletRequest request) {
		String newsId = request.getParameter("newsId");
		if (StrKit.isNotEmpty(newsId)) {
			News news = newsDao.getNewsById(newsId);
			request.setAttribute("news", news);
		}
		List<NewsType> newsTypeList = newsTypeDao.newsTypeList();
		request.setAttribute("newsTypeList", newsTypeList);
		if (StrKit.isNotEmpty(newsId)) {
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻管理", "新闻修改"));
		} else {
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻管理", "新闻添加"));
		}
		request.setAttribute("mainPage", "/background/news/newsSave.jsp");
		return "/background/mainTemp";
	}

	@RequestMapping("/save")
	@ApiOperation(httpMethod = "POST", value = "保存", notes = "保存")
	private String newsSave(@RequestParam(value = "file", required = false) MultipartFile files, News news,
			HttpServletRequest request) {
		if (news.getNewsId() == null) {
			newsDao.newsAdd(news);
		} else {
			newsDao.newsUpdate(news);
		}
		return "forward:/news/backList";
	}

	@RequestMapping("/backList")
	@ApiOperation(httpMethod = "POST", value = "新闻", notes = "新闻")
	private String newsBackList(HttpServletRequest request) {
		String s_bPublishDate = request.getParameter("s_bPublishDate");
		String s_aPublishDate = request.getParameter("s_aPublishDate");
		String s_title = request.getParameter("s_title");
		String page = request.getParameter("page");
		HttpSession session = request.getSession();
		if (StrKit.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_bPublishDate", s_bPublishDate);
			session.setAttribute("s_aPublishDate", s_aPublishDate);
			session.setAttribute("s_title", s_title);
		} else {
			s_bPublishDate = (String) session.getAttribute("s_bPublishDate");
			s_aPublishDate = (String) session.getAttribute("s_aPublishDate");
			s_title = (String) session.getAttribute("s_title");
		}
		News s_news = new News();
		if (StrKit.isNotEmpty(s_title)) {
			s_news.setTitle(s_title);
		}
		int total = newsDao.newsCount(s_news, s_bPublishDate, s_aPublishDate);
		String pageCode = PageUtil.getPagation(request.getContextPath() + "/news/backList", total,
				Integer.parseInt(page), PageUtil.backPageSize);
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageUtil.backPageSize);
		List<News> newsBackList = newsDao.newsListPage(s_news, pageBean, s_bPublishDate, s_aPublishDate);
		request.setAttribute("pageCode", pageCode);
		request.setAttribute("newsBackList", newsBackList);
		request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻管理", "新闻列表"));
		request.setAttribute("mainPage", "/background/news/newsList.jsp");
		return "/background/mainTemp";
	}

	@RequestMapping("/delete")
	@ApiOperation(httpMethod = "POST", value = "新闻删除", notes = "新闻删除")
	private String newsDelete(HttpServletRequest request) throws ServletException, IOException {
		String newsId = request.getParameter("newsId");
		int delNums = newsDao.newsDelete(newsId);
		String delFlag = "";
		if (delNums == 1) {
			delFlag = "ok";
		} else {
			delFlag = "error";
		}
		return delFlag;
	}

	/**
	 * 查询首页新闻
	 * 
	 * @Title: selectMainNews
	 * @Description: 查询首页新闻
	 * @author jianglingyun
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<List<News>> 返回类型
	 * @throws @date
	 *             2018年7月3日
	 */
	@RequestMapping("/selectMainNews")
	@ApiOperation(httpMethod = "POST", value = "主页新闻", notes = "主页新闻")
	private ResultMsg<List<TNews>> selectMainNews() {
		ResultMsg<List<TNews>> rs = new ResultMsg<>();
		List<TNews> list = newsService.selectMainNews();
		rs.setData(list);
		rs.setCode(Constant.CODE_SUCC);
		return rs;
	}

	@RequestMapping(value = "/getNews")
	@ApiOperation(httpMethod = "POST", value = "新闻资讯浏览", notes = "新闻资讯浏览")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "newsNote", value = "新闻标签", dataType = "string", paramType = "query") })
	public ResultMsg<List<TNews>> getNews(HttpServletRequest req) {
		String newsNote = req.getParameter("newsNote");
		ResultMsg<List<TNews>> resultMsg = new ResultMsg<>();
		List<TNews> iNews = newsService.selectNews(newsNote);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(iNews);
		return resultMsg;
	}

	/**
	 * 首页新闻
	* @Title: newsHome 
	* @Description: 首页新闻
	* @author jianglingyun
	* @param @param req
	* @param @return  参数说明 
	* @return ResultMsg<List<TNews>> 返回类型 
	* @throws 
	* @date 2018年7月9日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newsHome")
	@ApiOperation(httpMethod = "POST", value = "首页新闻", notes = "首页新闻")
	public ResultMsg<Map<String,Object>> newsHome(HttpServletRequest req) {
		ResultMsg<Map<String,Object>> resultMsg = null;
		
		resultMsg =(ResultMsg<Map<String, Object>>) redisService.get(Constant.NEWSHOME);
		if(resultMsg==null) {
			resultMsg = new ResultMsg<>();
			List<TNews> iNews = newsService.selectNewsHome();
			int size = iNews.size();
			Map<Integer,List<TNews>> result = new HashMap<>();
			//所有分类
			Map<String,Object> cateMap = new HashMap<>();
			List<List<TNews>> list = new ArrayList<>();
			//热门新
			List<TNews> hotList = new ArrayList<>(8);
			//有图片的新闻
			List<TNews> imgList = new ArrayList<>(8);
			//头条
			TNews headNews = null;
			for(int i=0;i<size;i++) {
				TNews n = iNews.get(i);
				
				if(1==n.getIsHot() && hotList.size()<8 ) { //热点
					hotList.add(n);
				}
				
				if(1==n.getIsImage() && hotList.size()<8 ) {//轮播图
					imgList.add(n);
				}
				
				if(headNews==null && 1==n.getIsHead() ) {//头条
					headNews = n;
				}
				
				if(result.keySet().contains(n.getTypeId())) {
					List<TNews> alist =result.get(n.getTypeId());
					if(alist.size()<10) {
						alist.add(n);
					}
					result.put(n.getTypeId(), alist);
				}else {
					List<TNews> alist =new ArrayList<>();
					alist.add(n);
					result.put(n.getTypeId(), alist);
				}
				
			}
			
			Iterator<Integer> it = result.keySet().iterator();
			while(it.hasNext()) {
				Integer key = it.next();
				list.add(result.get(key));
			}
			
			cateMap.put("cate", list);
			cateMap.put("imgList", imgList);
			cateMap.put("headNews", headNews);
			cateMap.put("hotList", hotList);
			
			
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(cateMap);
			redisService.set(Constant.NEWSHOME,resultMsg,7200L,TimeUnit.SECONDS);
		}
		
		return resultMsg;
	}
	
//	/**
//	 * 查询某一类新闻列表
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/getTypeNews")
//	@ApiOperation(httpMethod = "POST", value = "查询某一类新闻列表", notes = "查询某一类新闻列表")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", required = true, paramType = "query"),
//	    @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", required = true, paramType = "query")
//	})
//	public ResultMsg<Object> getTypeNews(@RequestParam("typeId") String typeId) {
//		ResultMsg<Object> rm = new ResultMsg<>();
//		Page<TNews> page = new PageFactory<TNews>().defaultPage();
//		List<NewsType> newsTypes =(List<NewsType>) redisService.get(Constant.NEWSTYPES);
//		if(newsTypes==null) {
//			newsTypes = newsTypeDao.newsTypeList(); //新闻所有分类
//			redisService.set(Constant.NEWSTYPES,newsTypes,7200L,TimeUnit.SECONDS);
//		}
//	
//		List<TNews> iNews = newsService.selectNewsType(page,typeId);
//		Map<String,Object> map = new HashMap<>();
//		map.put("news", iNews);
//		map.put("newsTypes", newsTypes);
//		rm.setCode(Constant.CODE_SUCC);
//		rm.setData(map);
//		return rm;
//		
//	}
	
	/**
	 * 获取新闻页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getNewsPage")
	@ApiOperation(httpMethod = "POST", value = "获取新闻页面", notes = "获取新闻页面")
	public ResultMsg<Object> getNewsPage(@RequestParam(required=false,value="typeId") String typeId) {
		ResultMsg<Object> rm = new ResultMsg<>();
		Page<TNews> page = new PageFactory<TNews>().defaultPage();
		List<NewsType> newsTypes =(List<NewsType>) redisService.get(Constant.NEWSTYPES);
		if(newsTypes==null) {
			newsTypes = newsTypeDao.newsTypeList(); //新闻所有分类
			redisService.set(Constant.NEWSTYPES,newsTypes,7200L,TimeUnit.SECONDS);
		}
		
		if(StrKit.isBlank(typeId)) {
			typeId = newsTypes.get(0).getNewsTypeId();
		}
		List<TNews> iNews = newsService.selectNewsType(page,typeId);
		Map<String,Object> map = new HashMap<>();
		map.put("news", iNews);
		map.put("newsTypes", newsTypes);
		rm.setCode(Constant.CODE_SUCC);
		rm.setData(map);
		return rm;
		
	}
}
