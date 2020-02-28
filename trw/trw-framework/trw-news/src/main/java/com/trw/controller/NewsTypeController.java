package com.trw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.mapper.NewsDao;
import com.trw.mapper.NewsTypeDao;
import com.trw.model.NewsType;
import com.trw.util.PageUtil;
import com.trw.util.StrKit;
 
@RestController
@RequestMapping("newsType")
public class NewsTypeController {

	@Autowired
	NewsDao newsDao;
	@Autowired
	NewsTypeDao newsTypeDao;
	
	@RequestMapping("/preSave")
	private String newsTypePreSave(HttpServletRequest request) {
		String newsTypeId=request.getParameter("newsTypeId");
		if(StrKit.isNotEmpty(newsTypeId)){
			NewsType newsType=newsTypeDao.getNewsTypeById(newsTypeId);
			request.setAttribute("newsType", newsType);
		}
		if(StrKit.isNotEmpty(newsTypeId)){
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻类别管理", "新闻类别修改"));
		}else{
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻类别管理", "新闻类别添加"));
		}
		request.setAttribute("mainPage", "/background/newsType/newsTypeSave.jsp");
		return "background/mainTemp";
		
	}
	@RequestMapping("/save")
	private String newsTypeSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsTypeId=request.getParameter("newsTypeId");
		String typeName=request.getParameter("typeName");
		
		NewsType newsType=new NewsType();
		newsType.setTypeName(typeName);
		
		if(StrKit.isNotEmpty(newsTypeId)){
			newsType.setNewsTypeId(newsTypeId);
		}
		if(StrKit.isNotEmpty(newsTypeId)){
			newsTypeDao.newsTypeUpdate(newsType);
		}else{
			newsTypeDao.newsTypeAdd(newsType);
		}
		return "forward:/newsType/backList";
	}
	
	@RequestMapping("/backList")
	private String newsTypeBackList(HttpServletRequest request) {
		List<NewsType> newsTypeBackList=newsTypeDao.newsTypeList();
		request.setAttribute("newsTypeBackList", newsTypeBackList);
		request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻类别管理", "新闻类别维护"));
		request.setAttribute("mainPage", "/background/newsType/newsTypeList.jsp");
		return "background/mainTemp";
		
	}
	
	@RequestMapping("/delete")
	private Map<String,String> newsTypeDelete(HttpServletRequest request){
		String newsTypeId=request.getParameter("newsTypeId");
		Map<String,String> map = new HashMap<>();
		Integer exist=newsDao.existNewsWithNewsTypeId(newsTypeId);
		if(exist!=null && exist>0){
			map.put("errorMsg", "该新闻类别下有新闻，不能删除此新闻类别");
		}else{
			int delNums=newsTypeDao.newsTypeDelete( newsTypeId);
			if(delNums>0){
				map.put("success", "ok");
			}else{
				map.put("errorMsg", "fail");
			}
		}
		return map;
	}
}
