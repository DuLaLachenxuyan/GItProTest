package com.trw.mapper;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTOrder;
import com.trw.vo.SerOrder;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
public interface SerOrderMapper extends BaseMapper<TrwTOrder> {
	/**
	 * 服务中心查看订单
	 * 
	 */
	List<SerOrder> findServerOrder(Map<String, String> param, Page<TrwTOrder> page);

	/**
	 * 
	* @Title: findFaciOrdDetail 
	* @Description: 服务中心订单详情
	* @author haochen
	* @param @param param
	* @param @return  参数说明 
	* @return SerOrder 返回类型    
	* @throws 
	* @date 2018年7月9日 下午2:46:35
	 */
	SerOrder findFaciOrdDetail(Map<String, String> param);

}
