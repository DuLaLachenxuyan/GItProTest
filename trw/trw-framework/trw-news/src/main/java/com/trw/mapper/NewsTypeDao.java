package com.trw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trw.model.NewsType;
@Mapper
public interface NewsTypeDao {
    @Select("select * from t_newsType")
	public List<NewsType> newsTypeList();
	
    @Select("select * from t_newsType where newsTypeId=#{newsTypeId}")
	public NewsType getNewsTypeById(@Param("newsTypeId") String newsTypeId);
	
    @Insert("insert into t_newsType(typeName) values(#{typeName})")
	public int newsTypeAdd(NewsType newsType);
	
    @Update("update t_newsType set typeName=#{typeName} where newsTypeId=#{newsTypeId}")
	public int newsTypeUpdate(NewsType newsType);
	
    @Delete("delete from t_newsType where newsTypeId=#{newsTypeId}")
	public int newsTypeDelete(@Param("newsTypeId") String newsTypeId);
}
