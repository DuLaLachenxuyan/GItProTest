package com.trw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceAgentRoleMapper;
import com.trw.model.ServiceAgentRole;
import com.trw.service.ServiceAgentRoleService;

/**
* @ClassName: ServiceAgentRoleServiceImpl 
* @Description: 服务商用户角色表 服务实现类
* @author luojing
* @date 2018年7月11日 下午1:59:24 
*
 */
@Service(value="serviceAgentRoleService")
public class ServiceAgentRoleServiceImpl extends ServiceImpl<ServiceAgentRoleMapper, ServiceAgentRole>
		implements ServiceAgentRoleService {
	
	@Autowired
	private ServiceAgentRoleMapper serviceAgentRoleMapper;

	@Override
	public boolean batchUpdateServiceAgentRole(List<ServiceAgentRole> list) {
		for(ServiceAgentRole agentRole : list) {
			EntityWrapper<ServiceAgentRole> wrapper = new EntityWrapper<ServiceAgentRole>(agentRole);
			serviceAgentRoleMapper.delete(wrapper);
		}
		return true;
	}
	
	@Override
	public ServiceAgentRole selectByAgentid(String agentid) {
		return baseMapper.selectByAgentid(agentid);
	}


}
