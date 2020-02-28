package com.trw.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AllocationMapper;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;
import com.trw.service.IAllocationService;
import com.trw.service.ILandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-20
 */
@Service
public class TrwTAllocationServiceImpl extends ServiceImpl<AllocationMapper, TrwTAllocation> implements IAllocationService {

	@Autowired
	public ILandService landService;
	
	@Override
	@Transactional(readOnly=false)
	public boolean saveAllocal(TrwTAllocation entity) {
		insert(entity);
		landService.allcalLand(entity.getProductid(),entity.getAgentId());
		return true;
	}
	
	@Override
	@Transactional(readOnly=false)
	public boolean saveAllAllotAgent(List<TrwTAllocation> allocations, List<TrwTLand> lands) {
		Boolean flg=insertBatch(allocations);
		Boolean fg=landService.updateBatchById(lands);
		if (fg==true && flg==true){
			return true;
		}else{
			return false;
		}
	}
	
}

