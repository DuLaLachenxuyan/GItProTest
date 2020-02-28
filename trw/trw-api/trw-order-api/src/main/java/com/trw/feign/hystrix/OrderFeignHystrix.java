package com.trw.feign.hystrix;


import org.springframework.stereotype.Service;

import com.trw.feign.OrderFeignApi;
import com.trw.model.TrwTOrder;
import com.trw.vo.ResultMsg;
@Service
public class OrderFeignHystrix implements OrderFeignApi {

	@Override
	public ResultMsg<TrwTOrder> updateOrder(TrwTOrder order) {
		return null;
	}

}
