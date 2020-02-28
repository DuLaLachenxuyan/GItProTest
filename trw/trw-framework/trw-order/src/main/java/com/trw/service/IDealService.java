package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTDeal;
import com.trw.vo.DealDetail;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author haochen123
 * @since 2018-06-30
 */
public interface IDealService extends IService<TrwTDeal> {

	/**
	 * * 查看服务中心的交易
	 */
	List<TrwTDeal> findDeal(Map<String, String> param, Page<TrwTDeal> page);

	/**
	 * 
	 * @Title: insertDeal
	 * @Description: 添加交易
	 * @author haochen
	 * @param @param
	 *            entity
	 * @param @param
	 *            agent 参数说明
	 * @return void 返回类型
	 * @throws @date
	 *             2018年7月25日 上午9:56:21
	 */
	void insertDeal(TrwTDeal entity);

	/**
	 * 
	* @Title: getDetail 
	* @Description: 查询交易详情
	* @author haochen
	* @param @param id
	* @param @return  参数说明 
	* @return DealDetail 返回类型    
	* @throws 
	* @date 2018年8月17日 上午11:25:11
	 */
	DealDetail   getDetail(String dealid);
	
}
