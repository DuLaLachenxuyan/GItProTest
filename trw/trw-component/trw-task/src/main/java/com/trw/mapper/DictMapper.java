package com.trw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTDict;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 */
public interface DictMapper extends BaseMapper<TrwTDict> {

	/**
	 * 根据编码获取词典列表
	 */
	List<TrwTDict> selectByCode(@Param("code") String code);

	/**
	 * 查询字典列表
	 */
	List<Map<String, Object>> list(@Param("condition") String conditiion);

	/**
	 * 查询分类码
	 * 
	 * @return
	 */
	List<TrwTDict> selectCode(@Param("types") String[] types);

}