package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTOrder;
import com.trw.vo.CliOrder;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-05-26
 */
public interface OrderMapper extends BaseMapper<TrwTOrder> {
	/**
	 * 
	 * @Title: findOrder
	 * @Description: 用户订单集合
	 * @author haochen
	 * @param @param
	 *            param
	 * @param @param
	 *            page
	 * @param @return
	 *            参数说明
	 * @return List<CliOrder> 返回类型
	 * @throws @date
	 *             2018年7月9日 下午2:08:08
	 */
	List<CliOrder> findOrder(Map<String, String> param, Page<TrwTOrder> page);

	/**
	 * 
	 * @Title: findOrdDetail
	 * @Description: 用户订单明细
	 * @author haochen
	 * @param @param
	 *            orderid
	 * @param @return
	 *            参数说明
	 * @return CliOrder 返回类型
	 * @throws @date
	 *             2018年7月9日 下午2:08:33
	 */
	CliOrder findOrdDetail(String orderid);

	/**
	 * 
	 * @Title: cancelOrder
	 * @Description: 取消订单
	 * @author haochen
	 * @param @param
	 *            entity
	 * @param @return
	 *            参数说明
	 * @return Boolean 返回类型
	 * @throws @date
	 *             2018年7月4日 上午10:25:38
	 */
	Boolean cancelOrder(TrwTOrder entity);

}
