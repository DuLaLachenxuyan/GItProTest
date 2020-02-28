package com.trw.feign.hystrix;


import org.springframework.stereotype.Service;

import com.trw.feign.AllocationFeignApi;
import com.trw.model.TrwTAllocation;
import com.trw.vo.ResultMsg;
@Service
public class AllocationFeignHystrix implements AllocationFeignApi{

	@Override
	public ResultMsg<TrwTAllocation> getAllocation(String productid) {
		// TODO Auto-generated method stub
		return null;
	}

}
