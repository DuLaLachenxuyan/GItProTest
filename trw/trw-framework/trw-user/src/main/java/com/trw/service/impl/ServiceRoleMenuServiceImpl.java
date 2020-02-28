package com.trw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceRoleMenuMapper;
import com.trw.model.ServiceRoleMenu;
import com.trw.service.ServiceRoleMenuService;

/**
* @ClassName: ServiceRoleMenuServiceImpl 
* @Description: 服务商角色菜单表 服务实现类
* @author luojing
* @date 2018年7月11日 下午1:59:40 
*
 */
@Service(value="serviceRoleMenuService")
public class ServiceRoleMenuServiceImpl extends ServiceImpl<ServiceRoleMenuMapper, ServiceRoleMenu>
		implements ServiceRoleMenuService {
	
	@Autowired
	private ServiceRoleMenuMapper serviceRoleMenuMapper;

	@Transactional
	@Override
	public boolean batchUpdateServiceRoleMenu(List<ServiceRoleMenu> list) {
		for(ServiceRoleMenu roleMenu : list) {
			//根据菜单ID和角色ID删除
			EntityWrapper<ServiceRoleMenu> wrapper = new EntityWrapper<ServiceRoleMenu>(roleMenu);
			serviceRoleMenuMapper.delete(wrapper);
		}
		return true;
	}

}
