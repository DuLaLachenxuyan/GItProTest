package com.trw.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceMenu;
import com.trw.vo.Tree;

/**
* @ClassName: ServiceMenuService 
* @Description: 服务商菜单表 服务类
* @author luojing
* @date 2018年7月11日 下午1:58:54 
*
 */
public interface ServiceMenuService extends IService<ServiceMenu> {
	
	/**
	* @Title: delServiceMenu 
	* @Description: 删除菜单
	* @author luojing
	* @param @param menuId
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean delServiceMenu(String menuId);
	
	/**
	* @Title: batchDelServiceMenu 
	* @Description: 批量删除菜单
	* @author luojing
	* @param @param ids
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean batchDelServiceMenu(List<String> ids);
	
	/**
	* @Title: selectTreeMenuByAgentId 
	* @Description: 根据经纪人ID查询菜单树
	* @author luojing
	* @param @param agentId
	* @param @return  参数说明 
	* @return List<Tree<ServiceMenu>> 返回类型 
	* @throws 
	* @date 2018年7月12日
	 */
	public List<Tree<ServiceMenu>> selectTreeMenuByAgentId(String agentId);
	
	/**
	* @Title: selectMenuTree 
	* @Description: 查询菜单树
	* @author luojing
	* @param @return  参数说明 
	* @return List<Tree<ServiceMenu>> 返回类型 
	* @throws 
	* @date 2018年7月12日
	 */
	public List<Tree<ServiceMenu>> selectMenuTree();
	
	/**
	* @Title: selectMenuByRoleId 
	* @Description: 根据角色ID查询菜单树
	* @author luojing
	* @param @param roleId
	* @param @return  参数说明 
	* @return List<Tree<ServiceMenu>> 返回类型 
	* @throws 
	* @date 2018年7月12日
	 */
	public List<Tree<ServiceMenu>> selectMenuTreeByRoleId(Integer roleId);
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
	public List<Integer> selectRoleMenuByRoleId(Integer roleId);
}
