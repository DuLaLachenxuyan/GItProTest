package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.constant.Constant;
import com.trw.feign.OrderFeignApi;
import com.trw.mapper.TrwTValuationMapper;
import com.trw.model.TrwTOrder;
import com.trw.model.TrwTValuation;
import com.trw.service.IValuationService;

@Service
public class ValuationService extends ServiceImpl<TrwTValuationMapper, TrwTValuation> implements IValuationService {

	@Autowired
	private OrderFeignApi orderFeignApi;

	@Override
	public List<Map<String,Object>> selectMyValution(Page<TrwTValuation> page, Map<String, String> param) {
		return baseMapper.selectMyValution(page, param);
	}

	@Override
	@Transactional
	public void insertValution(TrwTValuation entity) {
		baseMapper.insert(entity);
		TrwTOrder order = new TrwTOrder();
		order.setOrderid(entity.getOrderid());
		// 评价完成状态
		order.setOrderstat(Constant.ORDER_EVAL);
		orderFeignApi.updateOrder(order);
	}

}
