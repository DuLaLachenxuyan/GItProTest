package com.trw.mapper;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.ServiceMenu;

/**
* @ClassName: ServiceMenuMapper 
* @Description: 服务商菜单表 Mapper 接口
* @author luojing
* @date 2018年7月11日 下午2:00:11 
*
 */
public interface ServiceMenuMapper extends BaseMapper<ServiceMenu> {

	/**
	* @Title: selectTreeMenuByAgentId 
	* @Description: 根据经纪人ID查询菜单
	* @author luojing
	* @param @param agentId
	* @param @return  参数说明 
	* @return List<ServiceMenu> 返回类型 
	* @throws 
	* @date 2018年7月12日
	 */
	public List<ServiceMenu> selectTreeMenuByAgentId(@RequestParam("agentId") String agentId);
	
}
