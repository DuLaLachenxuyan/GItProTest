package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.constant.Constant;
import com.trw.feign.AgentDetailFeignApi;
import com.trw.mapper.SerOrderMapper;
import com.trw.model.AgentDetail;
import com.trw.model.TrwTOrder;
import com.trw.service.ISerOrderService;
import com.trw.util.StrKit;
import com.trw.vo.SerOrder;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
@Service
public class SerOrderServiceImpl extends ServiceImpl<SerOrderMapper, TrwTOrder> implements ISerOrderService {

	@Autowired
	private SerOrderMapper orderMapper;
	@Autowired
	private AgentDetailFeignApi agentDetailFeignApi;

	@Override
	public List<SerOrder> findServerOrder(Map<String, String> param, Page<TrwTOrder> page) {
		return orderMapper.findServerOrder(param, page);
	}

	@Override
	public SerOrder findFaciOrdDetail(Map<String, String> param) {
		return orderMapper.findFaciOrdDetail(param);
	}

	@Override
	@Transactional
	public void insertServiceOrder(TrwTOrder trwTOrder) {
		orderMapper.insert(trwTOrder);
		if (StrKit.equals(trwTOrder.getOrderstat(), Constant.ORDER_FINISH)) {
			AgentDetail agentDetail = agentDetailFeignApi.getAgentDetailById(trwTOrder.getAgentId()).getData();
			// 增加经纪人的带看次数
			agentDetail.setLookNum(agentDetail.getLookNum() + 1);
			agentDetailFeignApi.updateAgentDetailById(agentDetail);
		}
	}

	@Override
	@Transactional
	public void updateFaciOrder(TrwTOrder order) {
		orderMapper.updateById(order);
		if (StrKit.equals(order.getOrderstat(), Constant.ORDER_FINISH)) {
			AgentDetail agentDetail = agentDetailFeignApi.getAgentDetailById(order.getAgentId()).getData();
			// 增加经纪人的带看次数
			agentDetail.setLookNum(agentDetail.getLookNum() + 1);
			agentDetailFeignApi.updateAgentDetailById(agentDetail);
		}
	}

}
