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
import com.trw.feign.CustomerFeignApi;
import com.trw.feign.LandFeignApi;
import com.trw.mapper.DealMapper;
import com.trw.model.AgentDetail;
import com.trw.model.LandClient;
import com.trw.model.TrwTDeal;
import com.trw.model.TrwTLand;
import com.trw.service.IDealService;
import com.trw.util.StrKit;
import com.trw.vo.DealDetail;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author haochen123
 * @since 2018-06-30
 */
@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, TrwTDeal> implements IDealService {

	@Autowired
	private DealMapper dealMapper;
	@Autowired
	private AgentDetailFeignApi agentDetailFeignApi;
	@Autowired
	private LandFeignApi landFeignApi;
	@Autowired
	private CustomerFeignApi customerFeignApi;

	@Override
	public List<TrwTDeal> findDeal(Map<String, String> param, Page<TrwTDeal> page) {
		return dealMapper.findDeal(param, page);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertDeal(TrwTDeal entity) {
		dealMapper.insert(entity);
		// 跟新经纪人明细交易次数
		AgentDetail agentDetail = agentDetailFeignApi.getAgentDetailById(entity.getAgentid()).getData();
		agentDetail.setDealNum(agentDetail.getDealNum() + 1);// 增加交易次数
		agentDetail.setId(entity.getAgentid());
		agentDetailFeignApi.updateAgentDetailById(agentDetail);
		// 跟新地源交易状态
		if (!StrKit.isBlank(entity.getProductid())) {
			TrwTLand trwTLand = new TrwTLand();
			trwTLand.setProductid(entity.getProductid());
			trwTLand.setLandState(Constant.CLIENT_FINISH);// 地源交易完成状态
			landFeignApi.updateByid(trwTLand);
		}
		// 跟新客源交易状态
		if (!StrKit.isBlank(entity.getNeedid())) {
			LandClient landClient = new LandClient();
			landClient.setNeedid(entity.getNeedid());
			landClient.setLandState(Constant.CLIENT_FINISH);// 客源交易完成状态
			customerFeignApi.updateClientById(landClient);
		}
	}

	@Override
	public DealDetail getDetail(String dealid) {
		
		return dealMapper.getDetail(dealid);
	}

}
