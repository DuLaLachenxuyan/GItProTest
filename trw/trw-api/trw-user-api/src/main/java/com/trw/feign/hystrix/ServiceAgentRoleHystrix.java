package com.trw.feign.hystrix;

import com.trw.feign.ServiceAgentRoleFeignApi;
import com.trw.model.ServiceAgentRole;
import com.trw.vo.ResultMsg;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 14:46 2018/7/16
 * @Modified By:
 */
@Service
public class ServiceAgentRoleHystrix implements ServiceAgentRoleFeignApi{
    @Override
    public ResultMsg<ServiceAgentRole> selectByAgentid(String id) {
    	ResultMsg<ServiceAgentRole>  rg= new ResultMsg<>();
        return rg;
    }
}
