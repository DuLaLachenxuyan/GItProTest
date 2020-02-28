package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-20
 */
public interface IAllocationService extends IService<TrwTAllocation> {

	boolean saveAllocal(TrwTAllocation entity);
	
	
	boolean saveAllAllotAgent(List<TrwTAllocation> allocations, List<TrwTLand> lands);

}
