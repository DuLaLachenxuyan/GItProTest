package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTFile;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-17
 */
public interface FileMapper extends BaseMapper<TrwTFile> {
	/**
	 * 用户发布土地保存图片
	 * @param img
	 * @param userId
	 */
	void updateFile(Map<String,Object> map);
	/**
	 * 查询的过期图片
	 * @return
	 */
	List<TrwTFile> selecOverData();
	/**
	 * 删除过期图片
	 * @return
	 */
	int deleteOverData();

}
