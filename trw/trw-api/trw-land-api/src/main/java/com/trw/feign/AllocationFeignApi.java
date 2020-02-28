package com.trw.feign;


import com.trw.model.TrwTAllocation;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "trw-land")
public interface AllocationFeignApi {
     /**
      * 
     * @Title: getAllocation 
     * @Description: 通过土地id去查询得到分配经纪人的信息
     * @author haochen
     * @param @param productid
     * @param @return  参数说明 
     * @return ResultMsg<TrwTAllocation> 返回类型    
     * @throws 
     * @date 2018年8月14日 下午3:21:01
      */
	@RequestMapping(value = "/getAllocation",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ResultMsg<TrwTAllocation> getAllocation(@RequestParam("productid") String productid);
}
