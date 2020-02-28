package com.trw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.trw.model.Link;

@Mapper
public interface LinkDao {
	@Select("select * from t_link order by orderNum")
	public List<Link> linkList();
	
	@Insert("insert into t_link(linkName,linkUrl,linkEmail,orderNum) values(#{linkName},#{linkUrl},#{linkEmail},#{orderNum})")
	public int linkAdd(Link link);
	
	@Update("update t_link set linkName=#{linkName},linkUrl=#{linkUrl},linkEmail=#{linkEmail},orderNum=#{orderNum} where linkId=#{linkId}")
	public int linkUpdate(Link link);
	
	@Delete("delete from t_link where linkId=#{linkId}")
	public int linkDelete(@Param("linkId") String linkId);
	
	@Select("select * from t_link where linkId=#{linkId}")
	public Link getLinkById(@Param("linkId") String linkId);
}
