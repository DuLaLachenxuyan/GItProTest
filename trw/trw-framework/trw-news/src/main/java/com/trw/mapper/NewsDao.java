package com.trw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.trw.model.News;
import com.trw.vo.PageBean;
 
@Mapper
public interface NewsDao {

	@Select("select * from t_news where ${type}=1 order by publishDate desc limit ${page},${pageSize}")
	public List<News> newsList(@Param("type") String type ,@Param("page") int page ,@Param("pageSize") int pageSize);
	
	@SelectProvider(type = NewsProvider.class, method = "newsList")  
	public List<News> newsListPage(News s_news,PageBean pageBean,String s_bPublishDate,String s_aPublishDate);
	
	@SelectProvider(type = NewsProvider.class, method = "newsCount")  
	public Integer newsCount(News s_news,String s_bPublishDate,String s_aPublishDate);
	
	@Select("select t1.newsId,t1.title,t1.content,t1.publishDate,t1.author,t1.typeId,t1.click,t1.isHead,t1.isImage,t1.imageName,t1.isHot,t2.typeName from t_news t1 inner join t_newsType t2 on t1.typeId=t2.newsTypeId where t1.newsId=#{newsId}")
	public News getNewsById(String newsId);
	
	@Update("update t_news set click=click+1 where newsId=#{newsId}")
	public int newsClick(@Param("newsId") String newsId);
	
	@Select("SELECT * FROM t_news WHERE newsId<#{newsId} ORDER BY newsId DESC LIMIT 1 ")
	public News getUpPageId(@Param("newsId") String newsId);
	
	@Select("SELECT * FROM t_news WHERE newsId>#{newsId} ORDER BY newsId DESC LIMIT 1")
	public News getDownPageId(@Param("newsId") String newsId);
	
	@Select("select 1 from t_news where typeId=#{typeId} LIMIT 1")
	public Integer existNewsWithNewsTypeId(@Param("typeId") String typeId);
	
	@Insert("insert into t_news(title,content,publishDate,author,typeId,typeName,click,isHead,isImage,imageName,isHot) values(#{title},#{content},now(),#{author},#{typeId},#{typeName},#{click},#{isHead},#{isImage},#{imageName},#{isHot})")
	public int newsAdd(News news);
	
	@Update("update t_news set title=#{title},content=#{content},author=#{author},typeId=#{typeId},isHead=#{isHead},isImage=#{isImage},imageName=#{imageName},isHot=#{isHot} where newsId=#{newsId}")
	public int newsUpdate(News news);
	
	@Delete("delete from t_news where newsId=#{newsId}")
	public int newsDelete(@Param("newsId") String newsId);

	@Select("select * from t_news,t_newsType where typeId=newsTypeId and typeId=#{newsTypeId} order by publishDate desc limit 0,8")
	public List<News> newsListByType(@Param("newsTypeId") String newsTypeId);
}