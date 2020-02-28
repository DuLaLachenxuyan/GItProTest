package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTValuation;

public interface TrwTValuationMapper extends BaseMapper<TrwTValuation> {
	/**
	 * 
	* @Title: selectMyValution 
	* @Description: 查看评价
	* @author haochen
	* @param @param page
	* @param @param param
	* @param @return  参数说明 
	* @return List<Map<String,Object>> 返回类型    
	* @throws 
	* @date 2018年8月3日 下午6:45:21
	 */
	List<Map<String,Object>> selectMyValution(Page<TrwTValuation> page, Map<String, String> param);

	

}
