package com.trw.mapper;


import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTBimg;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-11
 */
public interface BannerMapper extends BaseMapper<TrwTBimg> {
	/**
	 * 查询banner
	* @Title: selectBanner 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author jianglingyun
	* @param @return  参数说明 
	* @return List<TrwTBimg> 返回类型 
	* @throws 
	* @date 2018年7月3日
	 */
	List<TrwTBimg> selectBanner();

}
