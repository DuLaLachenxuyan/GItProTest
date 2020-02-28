package com.trw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.ServiceAgentRole;

/**
* @ClassName: ServiceAgentRoleMapper 
* @Description: 服务商用户角色表 Mapper 接口
* @author luojing
* @date 2018年7月11日 下午2:00:01 
*
 */
public interface ServiceAgentRoleMapper extends BaseMapper<ServiceAgentRole> {
    
    ServiceAgentRole selectByAgentid(String agentid);

}
