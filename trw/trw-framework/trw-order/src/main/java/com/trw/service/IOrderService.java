package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTOrder;
import com.trw.vo.CliOrder;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jianglingyun
 * @since 2018-05-26
 */
public interface IOrderService extends IService<TrwTOrder> {
	/**
	 * 
	 * @Title: findOrder
	 * @Description: 用户的订单
	 * @author haochen
	 * @param @param
	 *            param
	 * @param @param
	 *            page
	 * @param @return
	 *            参数说明
	 * @return List<CliOrder> 返回类型
	 * @throws @date
	 *             2018年7月9日 下午2:12:23
	 */
	List<CliOrder> findOrder(Map<String, String> param, Page<TrwTOrder> page);

	/**
	 * 
	 * @Title: findOrdDetail
	 * @Description: 订单详情
	 * @author haochen
	 * @param @param
	 *            orderid
	 * @param @return
	 *            参数说明
	 * @return CliOrder 返回类型
	 * @throws @date
	 *             2018年7月9日 下午2:11:43
	 */
	CliOrder findOrdDetail(String orderid);

	/* 取消订单 */
	Boolean cancelOrder(TrwTOrder entity);

	/**
	 * 修改订单状态，记录用户流水
	 * 
	 * @param order
	 * @return
	 */
	void updateOrder(TrwTOrder order, Map<String, String> params);
    /**
     * 
    * @Title: updateOrderById 
    * @Description: 用户确认完成订单
    * @author haochen
    * @param @param order
    * @param @return  参数说明 
    * @return void	 返回类型    
    * @throws 
    * @date 2018年8月15日 下午9:49:57
     */
	void updateOrderById(TrwTOrder order);

}
