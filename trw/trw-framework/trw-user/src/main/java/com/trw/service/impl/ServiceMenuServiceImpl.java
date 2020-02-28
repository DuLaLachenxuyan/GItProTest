package com.trw.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceMenuMapper;
import com.trw.mapper.ServiceRoleMenuMapper;
import com.trw.model.ServiceMenu;
import com.trw.service.ServiceMenuService;
import com.trw.util.BuildTree;
import com.trw.vo.Tree;

/**
* @ClassName: ServiceMenuServiceImpl 
* @Description:  服务商菜单表 服务实现类
* @author luojing
* @date 2018年7月11日 下午1:59:31 
*
 */
@Service(value="serviceMenuService")
public class ServiceMenuServiceImpl extends ServiceImpl<ServiceMenuMapper, ServiceMenu> 
		implements ServiceMenuService {
	
	@Autowired
	private ServiceMenuMapper serviceMenuMapper;
	@Autowired
	private ServiceRoleMenuMapper serviceRoleMenuMapper;

	@Transactional
	@Override
	public boolean delServiceMenu(String menuId) {
		//删除菜单
		Integer i = serviceMenuMapper.deleteById(menuId);
		if(i>0) {
			//删除下级菜单
			deleteSubsetMenu(menuId);
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean batchDelServiceMenu(List<String> ids) {
		Integer i = serviceMenuMapper.deleteBatchIds(ids);
		if(i>0) {
			for(String id : ids) {
				//删除下级菜单
				deleteSubsetMenu(id);
			}
			return true;
		}
		return false;
	}
	
	/**
	* @Title: deleteSubsetMenu 
	* @Description: 删除下级菜单
	* @author luojing
	* @param @param menuId  参数说明 
	* @return void 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	private void deleteSubsetMenu(String menuId){
		ServiceMenu menu = new ServiceMenu();
		menu.setParentId(menuId);
		EntityWrapper<ServiceMenu> wrapper = new EntityWrapper<ServiceMenu>(menu);
		delete(wrapper);
	}

	@Override
	public List<Tree<ServiceMenu>> selectTreeMenuByAgentId(String agentId) {
		//查询菜单
		List<ServiceMenu> menuList = serviceMenuMapper.selectTreeMenuByAgentId(agentId);
		return getTree(menuList);
	}

	@Override
	public List<Tree<ServiceMenu>> selectMenuTree() {
		//查询菜单
		List<ServiceMenu> menuList = serviceMenuMapper.selectList(null);
		return getTree(menuList);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<Tree<ServiceMenu>> selectMenuTreeByRoleId(Integer roleId) {
		List<Tree<ServiceMenu>> trees = new ArrayList<Tree<ServiceMenu>>();
		//根据角色ID查询菜单ID
		List<Integer> roleMenuList = serviceRoleMenuMapper.selectRoleMenuByRoleId(roleId);
		if(!roleMenuList.isEmpty()) {
			//查询所有菜单
			List<ServiceMenu> menus = serviceMenuMapper.selectList(null);
			for (ServiceMenu menu : menus) {
				Tree<ServiceMenu> tree = new Tree<ServiceMenu>();
				//设置菜单信息
				tree.setId(menu.getMenuId().toString());
				tree.setParentId(menu.getParentId().toString());
				tree.setText(menu.getText());
				tree.setIcon(menu.getIcon());
				tree.setUrl(menu.getUrl());
				if(roleMenuList.contains(menu.getMenuId())) {
					tree.setChecked(true);
				}
				trees.add(tree);
			}
			return BuildTree.buildList(trees);
		}
		return trees;
	}
	
	/**
	* @Title: getTree 
	* @Description: 返回树结构
	* @author luojing
	* @param @param menuList
	* @param @return  参数说明 
	* @return List<Tree<ServiceMenu>> 返回类型 
	* @throws 
	* @date 2018年7月12日
	 */
	public List<Tree<ServiceMenu>> getTree(List<ServiceMenu> menuList){
		List<Tree<ServiceMenu>> trees = new ArrayList<Tree<ServiceMenu>>();
		if(!menuList.isEmpty()) {
			for(ServiceMenu menu : menuList) {
				Tree<ServiceMenu> tree = new Tree<ServiceMenu>();
				//设置菜单信息
				tree.setId(menu.getMenuId().toString());
				tree.setParentId(menu.getParentId().toString());
				tree.setText(menu.getText());
				tree.setIcon(menu.getIcon());
				tree.setUrl(menu.getUrl());
				trees.add(tree);
			}
			//构建树菜单
			return BuildTree.buildList(trees);
		}
		return trees;
	}
	
	/**
	 * 根据角色ID查询菜单ID
	* @Title: selectRoleMenuByRoleId 
	* @Description:根据角色ID查询菜单ID
	* @author jianglingyun
	* @param @param roleId
	* @param @return  参数说明 
	* @return List<Integer> 返回类型 
	* @throws 
	* @date 2018年8月3日
	 */
	@Override
	public List<Integer> selectRoleMenuByRoleId(Integer roleId) {
		return serviceRoleMenuMapper.selectRoleMenuByRoleId(roleId);
	}
}
