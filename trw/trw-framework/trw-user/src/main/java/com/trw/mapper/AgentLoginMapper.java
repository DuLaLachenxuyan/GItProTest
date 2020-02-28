package com.trw.mapper;
 
import java.util.Set;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTAgent;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianglingyun
 * @since 2018-05-29
 */
public interface AgentLoginMapper extends BaseMapper<TrwTAgent> {
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
	Set<String> getRoleByAgentId(String agentId);
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
	Set<String> getAgentPermissions(String agentId);
	Set<String> getAllPermissions();

}
