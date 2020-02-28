package com.trw.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.ServiceRole;

/**
* @ClassName: ServiceRoleMapper 
* @Description: 服务商角表 Mapper 接口
* @author luojing
* @date 2018年7月11日 下午2:00:18 
*
 */
public interface ServiceRoleMapper extends BaseMapper<ServiceRole> {

	List<ServiceRole> getAllRole();

}
