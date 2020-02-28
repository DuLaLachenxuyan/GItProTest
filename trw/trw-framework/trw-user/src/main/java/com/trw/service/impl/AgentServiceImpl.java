package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AgentDetailMapper;
import com.trw.mapper.AgentMapper;
import com.trw.mapper.ServiceAgentRoleMapper;
import com.trw.model.AgentDetail;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;
import com.trw.service.IAgentService;
import com.trw.service.ServiceAgentRoleService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-11
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, TrwTAgent> implements IAgentService {
	@Autowired
	AgentDetailMapper agentDetailMapper;
	@Autowired
	ServiceAgentRoleMapper serviceAgentRoleMapper;
	@Autowired
	ServiceAgentRoleService serviceAgentRoleService;

	/**
	 * 查询经纪人信息
	 *
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectAgent(Page<TrwTAgent> page, String faciid) {
		return baseMapper.selectAgent(page, faciid);
	}

	/**
	 * 查询经纪人和土地信息
	 *
	 * @param page
	 * @return
	 */
	@Override
	public List<TrwTAgent> selectLandAgent(Page<TrwTAgent> page) {
		return baseMapper.selectLandAgent(page);
	}

	@Override
	public int updateByPhone(TrwTAgent agent) {
		return baseMapper.updateByPhone(agent);
	}

	/**
	 * 根据服务商id查询员工列表
	 */
	@Override
	public List<Map<String, Object>> selectByfaciid(Page<TrwTAgent> page, Map<String, String> param) {

		return baseMapper.selectByfaciid(page, param);
	}

	@Override
	@Transactional
	public void insertAgentInfo(TrwTAgent agent, ServiceAgentRole role) {
		// 添加员工基本信息
		insert(agent);
		// 插入员工详细信息
		AgentDetail emp = new AgentDetail();
		emp.setId(agent.getId());
		// 交易次数
		agentDetailMapper.insert(emp);
		// 插入员工角色
		serviceAgentRoleMapper.insert(role);

	}

	@Override
	@Transactional
	public Boolean updateaAgent(TrwTAgent agent, String roleId) {
		// 获取经纪人角色主键
		Wrapper<ServiceAgentRole> wrapper = new EntityWrapper<ServiceAgentRole>().eq("agentId", agent.getId());
		ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectOne(wrapper);
		String arId = serviceAgentRole.getArId();
		if (arId==null) {
			return false; //角色信息为空返回false
		}

		ServiceAgentRole entity = new ServiceAgentRole();
	
		entity.setArId(arId);
		entity.setAgentId(agent.getId());
		entity.setRoleId(roleId);
		boolean flg = serviceAgentRoleService.updateById(entity);
		
		boolean updateById = updateById(agent);
		if (flg && updateById) {
			return true;
		}

		return false;
	}

}
