package com.trw.feign;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trw.model.TrwTAgent;

/**
 * 
* @ClassName: AgentLoginApi 
* @Description: 登录授权
* @author jianglingyun
* @date 2018年8月17日 下午2:46:48 
*
 */
@FeignClient(value = "trw-user")
public interface AgentLoginApi {
	/**
	 * 
	* @Title: getAgent 
	* @Description: 获取经纪人 
	* @author jianglingyun
	* @param @param user
	* @param @return  参数说明 
	* @return TrwTAgent 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@RequestMapping(value = "/getAgent",method=RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	TrwTAgent getAgent(TrwTAgent user);
	/**
	 * 
	* @Title: getAgent 
	* @Description: 获取经纪人角色
	* @author jianglingyun
	* @param @param user
	* @param @return  参数说明 
	* @return TrwTAgent 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@RequestMapping(value = "/getRoleByAgentId",method=RequestMethod.POST , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	Set<String> getRoleByAgentId(@RequestParam("agentId") String id);
	/**
	* @Title: getAgentPermissions 
	* @Description: 获取经纪人权限
	* @author jianglingyun
	* @param @param user
	* @param @return  参数说明 
	* @return Set 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@RequestMapping(value = "/getAgentPermissions",method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	Set<String> getAgentPermissions(@RequestParam("agentId") String id);

	@RequestMapping(value = "/getAllPermissions",method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Set<String> getAllPermissions();
}
