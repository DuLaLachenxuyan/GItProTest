package com.trw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.trw.model.Comment;
import com.trw.vo.PageBean;

@Mapper
public interface CommentDao {
	@SelectProvider(type = CommentProvider.class, method = "commentList")  
	public List<Comment> commentList(Comment s_comment,PageBean pageBean,String bCommentDate,String aCommentDate);

	@SelectProvider(type = CommentProvider.class, method = "getCount")  
	public int commentCount(Comment s_comment,String bCommentDate,String aCommentDate);

	@Insert("insert into t_comment(newsId,content,userIP,commentDate) values (#{newsId},#{content},#{userIP},now())")
	public int commentAdd(Comment comment);

	@Delete("delete from t_comment where commentId = #{commentIds}")
	public int commentDelete(@Param("commentIds") String commentIds);
}
