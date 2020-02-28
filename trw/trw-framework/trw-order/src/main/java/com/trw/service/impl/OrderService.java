package com.trw.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.constant.Constant;
import com.trw.feign.AgentDetailFeignApi;
import com.trw.feign.UserFeignApi;
import com.trw.mapper.OrderMapper;
import com.trw.model.AgentDetail;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.model.TrwTOrder;
import com.trw.service.IOrderService;
import com.trw.util.ToolUtil;
import com.trw.vo.CliOrder;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jianglingyun
 * @since 2018-05-26
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, TrwTOrder> implements IOrderService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserFeignApi userFeignApi;
	@Autowired
	private AgentDetailFeignApi agentDetailFeignApi;

	@Override
	public List<CliOrder> findOrder(Map<String, String> param, Page<TrwTOrder> page) {

		return baseMapper.findOrder(param, page);
	}

	@Override
	public Boolean cancelOrder(TrwTOrder entity) {
		return baseMapper.cancelOrder(entity);
	}

	@Override
	public CliOrder findOrdDetail(String orderid) {
		return baseMapper.findOrdDetail(orderid);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateOrder(TrwTOrder order, Map<String, String> params) {
		// 修改订单状态
		order.setOrderstat(Constant.ORDER_PAYED);
		baseMapper.updateById(order);
		// 修改账户信息
		// 判断用户是否存在账户
		TrwTAccount account = new TrwTAccount();
		account = userFeignApi.getAccountById(order.getUserId()).getData();
		if (account == null) {
			account = new TrwTAccount();
			account.setUserid(order.getUserId());
			account.setBalance(new BigDecimal(0));
			if (Constant.DEP_TIME.equals(order.getTakemodel())) { // 保证金看地
				account.setDeposit(order.getFee());
				String delooktime = redisService.getString(Constant.CONFIGPIX + "delooktime");
				account.setRetimes(Integer.parseInt(delooktime));
			} else {
				account.setRetimes(order.getNum());
			}
			// 插入一个账户
			userFeignApi.addAccount(account);
		}
		// 记录流水
		TrwTAccDetail detail = new TrwTAccDetail();
		detail.setId(ToolUtil.generateId("LSH"));
		detail.setBuyerLogonId(params.get("buyer_logon_id"));
		detail.setBody(params.get("body"));
		detail.setTradeNo(params.get("trade_no"));
		detail.setTotalAmount(new BigDecimal(params.get("total_amount")));
		detail.setFundFlow(Constant.OUTPUT);
		detail.setUserid(order.getUserId());
		detail.setOrderId(order.getOrderid());
		if (Constant.DEP_TIME.equals(order.getTakemodel())) { // 保证金看地
			detail.setFundType(Constant.DEPOSIT);
		} else {
			detail.setFundType(Constant.NORMAL);
		}
		detail.setCreateTime(LocalDateTime.now());
		userFeignApi.addAccDetail(detail);

	}

	@Override
	@Transactional
	public void updateOrderById(TrwTOrder order) {
		baseMapper.updateById(order);
		AgentDetail agentDetail = agentDetailFeignApi.getAgentDetailById(order.getAgentId()).getData();
		// 增加带看次数
		agentDetail.setLookNum(agentDetail.getLookNum() + 1);
		agentDetailFeignApi.updateAgentDetailById(agentDetail);
	}

}
