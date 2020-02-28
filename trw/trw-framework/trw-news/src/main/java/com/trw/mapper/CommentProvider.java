package com.trw.mapper;

import com.trw.model.Comment;
import com.trw.util.StrKit;
import com.trw.vo.PageBean;

public class CommentProvider {
	public String getCount(Comment s_comment, String bCommentDate, String aCommentDate) {
		StringBuilder sb = new StringBuilder("select count(*) as total from t_comment");
		if (s_comment.getNewsId() != -1) {
			sb.append(" and newsId=" + s_comment.getNewsId());
		}
		if (StrKit.isNotEmpty(bCommentDate)) {
			sb.append(" and TO_DAYS(commentDate)>=TO_DAYS('" + bCommentDate + "')");
		}
		if (StrKit.isNotEmpty(aCommentDate)) {
			sb.append(" and TO_DAYS(commentDate)<=TO_DAYS('" + aCommentDate + "')");
		}
		return sb.toString().replaceFirst("and", "where");
	}

	public String commentList(Comment s_comment, PageBean pageBean, String bCommentDate, String aCommentDate) {
		StringBuffer sb = new StringBuffer("select * from t_comment t1,t_news t2 where t1.newsId=t2.newsId");
		if (s_comment.getNewsId() != -1) {
			sb.append(" and t1.newsId=" + s_comment.getNewsId());
		}
		if (StrKit.isNotEmpty(bCommentDate)) {
			sb.append(" and TO_DAYS(t1.commentDate)>=TO_DAYS('" + bCommentDate + "')");
		}
		if (StrKit.isNotEmpty(aCommentDate)) {
			sb.append(" and TO_DAYS(t1.commentDate)<=TO_DAYS('" + aCommentDate + "')");
		}
		sb.append(" order by t1.commentDate desc ");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}
		return sb.toString();
	}
}