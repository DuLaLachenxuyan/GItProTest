package com.trw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceAgentRoleMapper;
import com.trw.mapper.ServiceRoleMapper;
import com.trw.mapper.ServiceRoleMenuMapper;
import com.trw.model.ServiceAgentRole;
import com.trw.model.ServiceRole;
import com.trw.model.ServiceRoleMenu;
import com.trw.service.ServiceRoleService;

/**
* @ClassName: ServiceRoleServiceImpl 
* @Description: 服务商角表 服务实现类
* @author luojing
* @date 2018年7月11日 下午1:59:48 
*
 */
@Service(value="serviceRoleService")
public class ServiceRoleServiceImpl extends ServiceImpl<ServiceRoleMapper, ServiceRole> 
		implements ServiceRoleService {
	
	@Autowired
	private ServiceRoleMapper serviceRoleMapper;
	@Autowired
	private ServiceAgentRoleMapper serviceAgentRoleMapper;
	@Autowired
	private ServiceRoleMenuMapper serviceRoleMenuMapper;
	
	@Transactional
	@Override
	public boolean delServiceRole(String roleId) {
		//删除角色
		int i = serviceRoleMapper.deleteById(roleId);
		if(i>0) {
			deleteAgentRoleAndRoleMenu(roleId);
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean batchDelServiceRole(List<String> ids) {
		//删除角色
		int i =  serviceRoleMapper.deleteBatchIds(ids);
		if(i>0) {
			for(String id : ids) {
				deleteAgentRoleAndRoleMenu(id);
			}
			return true;
		}
		return false;
	}
	
	/**
	* @Title: deleteAgentRoleAndRoleMenu 
	* @Description: 删除关联
	* @author luojing
	* @param @param roleId  参数说明 
	* @return void 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	private void deleteAgentRoleAndRoleMenu(String roleId) {
		//删除角色对应用户
		ServiceAgentRole agentRole = new ServiceAgentRole();
		agentRole.setRoleId(roleId);
		EntityWrapper<ServiceAgentRole> agentRoleWrapper = new EntityWrapper<ServiceAgentRole>(agentRole);
		serviceAgentRoleMapper.delete(agentRoleWrapper);
		//删除角色对应菜单
		ServiceRoleMenu roleMenu = new ServiceRoleMenu();
		roleMenu.setRoleId(roleId);
		EntityWrapper<ServiceRoleMenu> roleMenuWrapper = new EntityWrapper<ServiceRoleMenu>(roleMenu);
		serviceRoleMenuMapper.delete(roleMenuWrapper);
	}

	@Override
	public List<ServiceRole> getAllRole() {
	 
		return baseMapper.getAllRole();
	}

}
