package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTFaci;
import com.trw.vo.OrderFaci;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
public interface FaciMapper extends BaseMapper<TrwTFaci> {
	
	List<TrwTFaci> selectMainFaci(Page<TrwTFaci> page, Map<String, String> map);

	List<TrwTFaci> getFaciByLoc(String location);
	/**
	 * 服务中心评价
	* @Title: selectOrderFaci 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author jianglingyun
	* @param @param page
	* @param @param faciid
	* @param @return  参数说明 
	* @return List<OrderFaci> 返回类型 
	* @throws 
	* @date 2018年7月3日
	 */
	List<OrderFaci> selectOrderFaci(Page<OrderFaci> page, String faciid);
	/**
	 * 通过经纪人获取中心
	* @Title: getAgentById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author jianglingyun
	* @param @param agentId
	* @param @return  参数说明 
	* @return TrwTFaci 返回类型 
	* @throws 
	* @date 2018年7月6日
	 */
	TrwTFaci getAgentById(String agentId);
}
