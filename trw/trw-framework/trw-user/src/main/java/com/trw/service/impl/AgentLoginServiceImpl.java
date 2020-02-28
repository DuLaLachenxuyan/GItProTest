package com.trw.service.impl;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AgentLoginMapper;
import com.trw.model.TrwTAgent;
import com.trw.service.IAgentLoginService;

@Service
public class AgentLoginServiceImpl extends ServiceImpl<AgentLoginMapper, TrwTAgent> implements IAgentLoginService {

	@Override
	public TrwTAgent getAgent(TrwTAgent user) {
		return baseMapper.selectOne(user);
	}
	/**
	 * 
	* @Title: getRoleByAgentId 
	* @Description: 查询经纪人角色
	* @author jianglingyun
	* @param @param agentId
	* @param @return  参数说明 
	* @return Set<String> 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@Override
	public Set<String> getRoleByAgentId(String agentId) {
		return baseMapper.getRoleByAgentId(agentId);
	}
	/**
	 * 
	* @Title: getAgentPermissions 
	* @Description:  查询经纪人权限
	* @author jianglingyun
	* @param @param agentId
	* @param @return  参数说明 
	* @return Set<String> 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@Override
	public Set<String> getAgentPermissions(String agentId) {
		return baseMapper.getAgentPermissions(agentId);
	}
	@Override
	public Set<String> getAllPermissions() {
		return baseMapper.getAllPermissions();
	}

}
