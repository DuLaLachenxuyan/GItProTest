package com.trw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.mapper.LinkDao;
import com.trw.model.Link;
import com.trw.util.PageUtil;
import com.trw.util.StrKit;

@RestController
@RequestMapping("/link")
public class LinkController{

	@Autowired
	LinkDao linkDao;
	
	@RequestMapping("/delete")
	private String linkDelete(HttpServletRequest request){
		String linkId=request.getParameter("linkId");
		int delNums=linkDao.linkDelete(linkId);
		String delFlag = "";
		if(delNums>0){
			delFlag = "ok";
		}else{
			delFlag = "fail";
		}
		return delFlag;
	}
	
	@RequestMapping("/backList")
	private String linkBackList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Link> linkBackList=linkDao.linkList();
		request.setAttribute("linkBackList", linkBackList);
		request.setAttribute("navCode", PageUtil.genNewsManageNavigation("友情链接管理", "友情链接维护"));
		request.setAttribute("mainPage", "/background/link/linkList.jsp");
		return "background/mainTemp";
	}

	@RequestMapping("/preSave")
	private String linkPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String linkId=request.getParameter("linkId");
		if(StrKit.isNotEmpty(linkId)){
			Link link=linkDao.getLinkById(linkId);
			request.setAttribute("link", link);
		}
		
		if(StrKit.isNotEmpty(linkId)){
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("友情链接管理", "友情链接修改"));
		}else{
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("友情链接管理", "友情链接添加"));
		}
		request.setAttribute("mainPage", "/background/link/linkSave.jsp");
		return "background/mainTemp";
	}
	
	@RequestMapping("/save")
	private String linkSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String linkId=request.getParameter("linkId");
		String linkName=request.getParameter("linkName");
		String linkUrl=request.getParameter("linkUrl");
		String linkEmail=request.getParameter("linkEmail");
		String orderNum=request.getParameter("orderNum");
		
		Link link=new Link(linkName, linkUrl, linkEmail, Integer.parseInt(orderNum));
		
		if(StrKit.isNotEmpty(linkId)){
			link.setLinkId(Integer.parseInt(linkId));
		}
		if(StrKit.isNotEmpty(linkId)){
			linkDao.linkUpdate(link);
		}else{
			linkDao.linkAdd(link);
		}
		return "forward:/link/backList";
	}
}
