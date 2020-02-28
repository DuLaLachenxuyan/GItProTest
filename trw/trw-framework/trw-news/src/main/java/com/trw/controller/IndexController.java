package com.trw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trw.mapper.LinkDao;
import com.trw.mapper.NewsDao;
import com.trw.mapper.NewsTypeDao;
import com.trw.model.Link;
import com.trw.model.News;
import com.trw.model.NewsType;

@RestController
public class IndexController {

	@Autowired
	private NewsTypeDao newsTypeDao;
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private LinkDao linkDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		List<NewsType> newsTypeList = newsTypeDao.newsTypeList();
		request.setAttribute("newsTypeList", newsTypeList);
		
		List<News> imageNewsList = newsDao.newsList("isImage", 0, 5);
		request.setAttribute("imageNewsList", imageNewsList);

		List<News> headNewsList = newsDao.newsList("isHead", 0, 1);
		News headNews = headNewsList.get(0);
		//headNews.setContent(StrKit.Html2Text(headNews.getContent()));
		request.setAttribute("headNews", headNews);

		List<News> hotSpotNewsList = newsDao.newsList("isHot", 0, 8);
		request.setAttribute("hotSpotNewsList", hotSpotNewsList);

		List<List<News>> allIndexNewsList = new ArrayList<>();
		if (newsTypeList != null && newsTypeList.size() != 0) {
			for (int i = 0; i < newsTypeList.size(); i++) {
				NewsType newsType = newsTypeList.get(i);
				List<News> oneSubList = newsDao.newsListByType(newsType.getNewsTypeId());
				allIndexNewsList.add(oneSubList);
			}
		}
		request.setAttribute("allIndexNewsList", allIndexNewsList);

		List<Link> linkList = linkDao.linkList();
		request.setAttribute("linkList", linkList);

		return "index";
	}

}
