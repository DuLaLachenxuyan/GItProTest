package com.trw.feign.hystrix;

import org.springframework.stereotype.Service;

import com.trw.feign.AgentDetailFeignApi;
import com.trw.model.AgentDetail;
import com.trw.vo.ResultMsg;

@Service
public class AgentDetailFeignHystrix implements AgentDetailFeignApi{

	@Override
	public ResultMsg<AgentDetail> updateAgentDetailById(AgentDetail agentDetail) {
		return null;
	}

	@Override
	public ResultMsg<AgentDetail> getAgentDetailById(String id) {
		return null;
	}

}
