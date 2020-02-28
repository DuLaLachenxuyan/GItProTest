package com.trw.config;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trw.constant.Constant;
import com.trw.feign.AgentLoginApi;
import com.trw.model.TrwTAgent;

public class MyShiroRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Autowired
	private AgentLoginApi agentApi;
    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        TrwTAgent user = new TrwTAgent();
        user.setAacount(name);
        // 从数据库获取对应用户名密码的用户
        TrwTAgent agent = agentApi.getAgent(user);
        if (agent != null ) {
            // 用户为禁用状态
        	if(!Constant.ASTAT_NORMAL.equals(agent.getAstat())) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            		agent, //用户
            		agent.getApwd(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException("用户不存在");
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof TrwTAgent) {
        	TrwTAgent userLogin = (TrwTAgent) principal;
        	Set<String> roles = agentApi.getRoleByAgentId(userLogin.getId());
            authorizationInfo.addRoles(roles);
            
            Set<String> permissions =null;
            if(roles.contains(Constant.ROLE_ADMIN)) {
            	 permissions = agentApi.getAllPermissions();
            }else {
            	 permissions = agentApi.getAgentPermissions(userLogin.getId());
            }
           
            authorizationInfo.addStringPermissions(permissions);
        }
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

}