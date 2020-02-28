package com.trw.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trw.model.TrwTAgent;
import com.trw.service.IAgentLoginService;

/**
 * <p>
 * 经纪人shiro查询
 * </p>
 *
 * @author jly
 * @since 2018-08-17
 */
@RestController
public class AgentLoginController {
	
	@Autowired
	private IAgentLoginService agentservice;

	@RequestMapping(value = "/getAgent",method=RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public TrwTAgent getAgent(@RequestBody TrwTAgent user) {
		TrwTAgent agent = agentservice.getAgent(user);
		return agent;
	}
	
	@RequestMapping(value = "/getRoleByAgentId",method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) 
	public Set<String> getRoleByAgentId(@RequestParam("agentId") String agentId) {
		Set<String> roles = agentservice.getRoleByAgentId(agentId);
		return roles;
	}
	
	@RequestMapping(value = "/getAgentPermissions",method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Set<String> getAgentPermissions(@RequestParam("agentId") String agentId){
		Set<String> permissions = agentservice.getAgentPermissions(agentId);
		return permissions;
	}
	
	@RequestMapping(value = "/getAllPermissions",method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Set<String> getAllPermissions(){
		Set<String> permissions = agentservice.getAllPermissions();
		return permissions;
	}
}
