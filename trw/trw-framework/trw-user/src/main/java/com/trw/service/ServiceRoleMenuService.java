package com.trw.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceRoleMenu;

/**
* @ClassName: ServiceRoleMenuService 
* @Description: 服务商角色菜单表 服务类
* @author luojing
* @date 2018年7月11日 下午1:59:06 
*
 */
public interface ServiceRoleMenuService extends IService<ServiceRoleMenu> {

	/**
	* @Title: batchUpdateServiceRoleMenu 
	* @Description: 为角色批量修改菜单信息
	* @author luojing
	* @param @param list
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean batchUpdateServiceRoleMenu(List<ServiceRoleMenu> list);
}
