package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTValuation;

public interface IValuationService extends IService<TrwTValuation>{
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
	* @date 2018年8月3日 下午6:45:53
	 */
	List<Map<String,Object>> selectMyValution(Page<TrwTValuation> page, Map<String, String> param);

	/**
	 * 
	* @Title: insertValution 
	* @Description:增添评价
	* @author haochen
	* @param @param entity  参数说明 
	* @return void 返回类型    
	* @throws 
	* @date 2018年7月24日 下午1:36:17
	 */
	void insertValution(TrwTValuation entity);

	

}
