package com.trw.feign.hystrix;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.trw.feign.AgentLoginApi;
import com.trw.model.TrwTAgent;

@Service
public class AgentLoginFeignHystrix implements AgentLoginApi {

	@Override
	public TrwTAgent getAgent(TrwTAgent user) {
		return null;
	}

	@Override
	public Set<String> getRoleByAgentId(String id) {
		return null;
	}

	@Override
	public Set<String> getAgentPermissions(String id) {
		return null;
	}

	@Override
	public Set<String> getAllPermissions() {
		return null;
	}

}
