package com.trw.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceAgentRole;

/**
* @ClassName: ServiceAgentRoleService 
* @Description: 服务商用户角色表 服务类
* @author luojing
* @date 2018年7月11日 下午1:58:44 
*
 */
public interface ServiceAgentRoleService extends IService<ServiceAgentRole> {

	/**
	* @Title: batchUpdateServiceAgentRole 
	* @Description: 为用户批量修改角色信息
	* @author luojing
	* @param @param list
	* @param @return  参数说明 
	* @return boolean 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	public boolean batchUpdateServiceAgentRole(List<ServiceAgentRole> list);
	
	
	/**
	 * 根据经纪人id查询对应的权限
	 * @param agentid
	 * @return
	 */
	ServiceAgentRole selectByAgentid(String agentid);
	
}
