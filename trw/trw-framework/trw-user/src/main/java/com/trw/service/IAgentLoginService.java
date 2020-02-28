package com.trw.service;

import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTAgent;

/**
 * 
* @ClassName: IAgentLoginService 
* @Description: 经纪人权限认证
* @author jianglingyun
* @date 2018年8月17日 下午12:21:08 
*
 */
public interface IAgentLoginService  extends IService<TrwTAgent> {

	TrwTAgent getAgent(TrwTAgent user);

	Set<String> getRoleByAgentId(String agentId);

	Set<String> getAgentPermissions(String agentId);

	Set<String> getAllPermissions();

}
