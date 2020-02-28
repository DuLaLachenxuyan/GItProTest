package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTFaci;
import com.trw.vo.OrderFaci;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
public interface IFaciService extends IService<TrwTFaci> {
	/**
	 * 查询主页活动中心列表
	 * @param map
	 * @return
	 */
	public List<TrwTFaci> selectMainFaci(Page<TrwTFaci> page,Map<String,String> map);
	
	/**
	 * 通过id获取服务中心
	 * @param id
	 * @return
	 */
	public TrwTFaci getFaci(String id);
	/**
	 * 通过区域获取服务中心
	 * @param id
	 * @return
	 */
	public TrwTFaci getFaciByLoc(String location);
	/**
	 * 服务中心的评价
	* @Title: selectOrderFaci 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author jianglingyun
	* @param @param page2
	* @param @param faciid
	* @param @return  参数说明 
	* @return List<OrderFaci> 返回类型 
	* @throws 
	* @date 2018年7月3日
	 */
	public List<OrderFaci> selectOrderFaci(Page<OrderFaci> page2, String faciid);
 

}
