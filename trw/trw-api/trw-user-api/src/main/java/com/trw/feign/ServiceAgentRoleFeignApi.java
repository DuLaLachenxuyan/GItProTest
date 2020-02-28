package com.trw.feign;

import com.trw.model.ServiceAgentRole;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 14:46 2018/7/16
 * @Modified By:
 */
@FeignClient(value = "trw-user")
public interface ServiceAgentRoleFeignApi {
    @RequestMapping(value="/selectByAgentid",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResultMsg<ServiceAgentRole> selectByAgentid(@RequestParam("id") String id);
}
