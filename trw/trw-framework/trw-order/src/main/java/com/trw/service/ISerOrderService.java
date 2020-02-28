package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTOrder;
import com.trw.vo.SerOrder;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
public interface ISerOrderService extends IService<TrwTOrder> {
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
	 * @param @param
	 *            param
	 * @param @return
	 *            参数说明
	 * @return SerOrder 返回类型
	 * @throws @date
	 *             2018年7月9日 下午2:47:20
	 */
	SerOrder findFaciOrdDetail(Map<String, String> param);

	/**
	 * 
	 * @Title: insertServiceOrder
	 * @Description: 生成带看
	 * @author haochen
	 * @param @param
	 *            trwTOrder 参数说明
	 * @return void 返回类型
	 * @throws @date
	 *             2018年7月25日 上午9:07:30
	 */
	void insertServiceOrder(TrwTOrder trwTOrder);

	/**
	 * 
	 * @Title: updateFaciOrder
	 * @Description: 更新服务中心的订单
	 * @author haochen
	 * @param @param
	 *            order 参数说明
	 * @return void 返回类型
	 * @throws @date
	 *             2018年8月23日 下午3:50:37
	 */
	void updateFaciOrder(TrwTOrder order);

}
