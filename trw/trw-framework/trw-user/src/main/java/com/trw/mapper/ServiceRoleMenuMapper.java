package com.trw.mapper;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.ServiceRoleMenu;

/**
* @ClassName: ServiceRoleMenuMapper 
* @Description: 服务商角色菜单表 Mapper 接口
* @author luojing
* @date 2018年7月11日 下午2:00:26 
*
 */
public interface ServiceRoleMenuMapper extends BaseMapper<ServiceRoleMenu> {

	public List<Integer> selectRoleMenuByRoleId(@RequestParam("roleId") Integer roleId);
}
