package com.trw.service.impl;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.NeedagentMapper;
import com.trw.model.TrwTNeedagent;
import com.trw.service.ILandClientService;
import com.trw.service.INeedagentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-29
 */
@Service
public class NeedagentServiceImpl extends ServiceImpl<NeedagentMapper, TrwTNeedagent> implements INeedagentService {
	@Autowired
	private ILandClientService trwtClientService;
	@Override
	@Transactional(readOnly=false,noRollbackFor=Exception.class)
	public boolean saveAgent(TrwTNeedagent entity) {
		insert(entity);
//		insertIn(entity);
		Boolean flg = trwtClientService.updateAgentId(entity.getNeedid(),entity.getAgentId());
		return flg;
	}
	
	@Override
	public boolean insertIn(TrwTNeedagent entiry) {
		return baseMapper.insertIn(entiry);
	}
	 


}
