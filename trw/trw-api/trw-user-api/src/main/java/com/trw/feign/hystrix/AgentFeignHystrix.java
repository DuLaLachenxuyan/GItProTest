package com.trw.feign.hystrix;

import org.springframework.stereotype.Service;

import com.trw.feign.AgentFeignApi;
import com.trw.model.TrwTAgent;
import com.trw.vo.ResultMsg;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 14:06 2018/7/5
 * @Modified By:
 */
@Service
public class AgentFeignHystrix implements AgentFeignApi {
    
    
    @Override
    public ResultMsg<TrwTAgent> getAgentById(String id) {
    	ResultMsg<TrwTAgent> re= new ResultMsg<>();
        return re;
    }

	@Override
	public ResultMsg<TrwTAgent> updateAgentById(TrwTAgent agent) {
		return null;
	}
}
