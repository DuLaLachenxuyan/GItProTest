package com.trw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.mapper.CommentDao;
import com.trw.model.Comment;
import com.trw.util.PageUtil;
import com.trw.util.StrKit;
import com.trw.vo.PageBean;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentDao dao;

	@RequestMapping("/save")
	public String commentSave(HttpServletRequest request)
			throws ServletException, IOException {
		String newsId = request.getParameter("newsId");
		String content = request.getParameter("content");
		String userIP = request.getRemoteAddr();
		Comment comment = new Comment(Integer.parseInt(newsId), content, userIP);
		dao.commentAdd(comment);
		return "redirect:/news/show?newsId=" + newsId;
	}

	@RequestMapping("/backList")
	private String commentBackList(HttpServletRequest request)
			throws ServletException, IOException {
		String s_bCommentDate = request.getParameter("s_bCommentDate");
		String s_aCommentDate = request.getParameter("s_aCommentDate");
		String page = request.getParameter("page");
		HttpSession session = request.getSession();
		if (StrKit.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_bCommentDate", s_bCommentDate);
			session.setAttribute("s_aCommentDate", s_aCommentDate);
		} else {
			s_bCommentDate = (String) session.getAttribute("s_bCommentDate");
			s_aCommentDate = (String) session.getAttribute("s_aCommentDate");
		}
		try {
			int total = dao.commentCount(new Comment(), s_bCommentDate, s_aCommentDate);
			String pageCode = PageUtil.getPagation(request.getContextPath() + "/comment?action=backList", total,
					Integer.parseInt(page), PageUtil.backPageSize);
			PageBean pageBean = new PageBean(Integer.parseInt(page),
					PageUtil.backPageSize);
			List<Comment> commentBackList = dao.commentList(new Comment(), pageBean, s_bCommentDate, s_aCommentDate);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("commentBackList", commentBackList);
			request.setAttribute("navCode", PageUtil.genNewsManageNavigation("新闻评论管理", "新闻评论维护"));
			request.setAttribute("mainPage", "/background/comment/commentList.jsp");
			return "background/mainTemp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/background/mainTemp";
	}

	@RequestMapping("/delete")
	private String commentDelete(HttpServletRequest request) throws Exception {
		String commentIds = request.getParameter("commentIds");
		int delNums = dao.commentDelete(commentIds);
		String delFlag = "";
		if(delNums>0){
			delFlag = "ok";
		}else{
			delFlag = "fail";
		}
		return delFlag;

	}
}
