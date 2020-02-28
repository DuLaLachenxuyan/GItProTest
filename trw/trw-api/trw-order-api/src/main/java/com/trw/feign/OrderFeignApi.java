package com.trw.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.trw.model.TrwTOrder;
import com.trw.vo.ResultMsg;

@FeignClient(value = "trw-order")
public interface OrderFeignApi {

	
	/**
	 * 
	* @Title: updateOrder 
	* @Description: 更新订单 
	* @author haochen
	* @param @param order
	* @param @return  参数说明 
	* @return ResultMsg<TrwTOrder> 返回类型    
	* @throws 
	* @date 2018年7月24日 下午2:21:07
	 */
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<TrwTOrder> updateOrder(@RequestBody TrwTOrder order);

}
