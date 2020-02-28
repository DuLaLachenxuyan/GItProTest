package com.trw.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceRole;

/**
* @ClassName: ServiceRoleService 
* @Description: 服务商角表 服务类
* @author luojing
* @date 2018年7月11日 下午1:59:13 
*
 */
public interface ServiceRoleService extends IService<ServiceRole> {
	
	/**
	* @Title: delServiceRole 
	* @Description: 删除角色信息
	* @author luojing
	* @param @param roleId
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean delServiceRole(String roleId);
	
	/**
	* @Title: batchDelServiceRole 
	* @Description: 批量删除角色信息
	* @author luojing
	* @param @param ids
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean batchDelServiceRole(List<String> ids);

	public List<ServiceRole> getAllRole();

}
