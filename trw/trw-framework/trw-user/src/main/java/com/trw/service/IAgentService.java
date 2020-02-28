package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-14
 */
public interface IAgentService extends IService<TrwTAgent> {
    /**
     * 查询经纪人信息
     * @param page
     * @return
     */
    List<Map<String,Object>> selectAgent(Page<TrwTAgent> page,String faciid);
    
    /**
     * 查询经纪人和土地信息
     * @param page
     * @return
     */
    List<TrwTAgent> selectLandAgent(Page<TrwTAgent> page);

	/**
	 * 
	* @Title: selectByfaciid 
	* @Description: 根据服务商id查询员工
	* @author haochen
	* @param @param page
	* @param @param param
	* @param @return  参数说明 
	* @return List<Map<String,Object>> 返回类型    
	* @throws 
	* @date 2018年8月2日 下午4:39:26
	 */
    List<Map<String, Object>> selectByfaciid(Page<TrwTAgent> page, Map<String, String> param);
	/**
	 * 
	* @Title: updateByPhone 
	* @Description: 根据电话修改密码 
	* @author haochen
	* @param @param agent
	* @param @return  参数说明 
	* @return int 返回类型    
	* @throws 
	* @date 2018年7月17日 
	 */
	public int updateByPhone(TrwTAgent agent);
	
	/**
	 * 添加员工基本信息
	* @Title: insertAgentInfo 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @author gongzhen
	 * @param role 
	* @param @param agent  参数说明 
	* @return void 返回类型 
	* @throws 
	* @date 2018年7月26日
	 */
	public void insertAgentInfo(TrwTAgent agent, ServiceAgentRole role);


	Boolean updateaAgent(TrwTAgent agent, String roleId);
	
 
}
