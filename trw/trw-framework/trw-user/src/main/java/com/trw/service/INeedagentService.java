package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTNeedagent;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-29
 */
public interface INeedagentService extends IService<TrwTNeedagent> {
	/**
	 * 保存客源分配经纪人信息
	* @Title: saveAgent 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author jianglingyun
	* @param @param entity
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月4日
	 */
	boolean saveAgent(TrwTNeedagent entity);
	
	boolean insertIn(TrwTNeedagent entiry);
	

}
