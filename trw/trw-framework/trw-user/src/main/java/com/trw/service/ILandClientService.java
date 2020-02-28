package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.LandClient;
import com.trw.model.TrwTNeedagent;
import com.trw.vo.ClientVo;

/**
 * <p>
 *  寻找土地
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-14
 */
public interface ILandClientService extends IService<LandClient> {

	List<LandClient> selectLandClient(Page<LandClient> page, Map<String, String> param);

	List<LandClient> selectByUserid(String userId, Page<LandClient> page);

	List<LandClient> selectByTerm(Page<LandClient> page, Map<String, String> param);
	
	List<ClientVo> selectClient(Page<LandClient> page, Map<String, String> param);
	/**
	 * 
	* @Title: updateAgentId 
	* @Description: 修改客源状态，改成已分配
	* @author jianglingyun
	* @param @param needid
	* @param @param agentId
	* @param @return  参数说明 
	* @return Boolean 返回类型 
	* @throws 
	* @date 2018年7月4日
	 */
	public Boolean updateAgentId(String needid, String agentId);
	
	/**
	 * 查询类似的土地需求
	 * @param map
	 * @return
	 */
	List<LandClient> selectSimilar(Page<LandClient> page,Map<String,String> map);
	
	/**
	 * 查询服务商下面的客户
	 * @param id
	 * @return
	 */
	List<LandClient> selectTrwClient(String id);
	
	/**
	 * 查询经纪人下面的客户
	 * @param agentid
	 * @return
	 */
	List<LandClient> selectAgentClient(String agentid);
	
	/**
	 * 根据土地id查询匹配过的需求
	 * @param id
	 * @return
	 */
	List<LandClient> selectMatched(String id);
	
	/**
	 * 服务商发布土地
	 * @param trwTNeedagent
	 * @param landClient
	 * @return
	 */
	Boolean insertClient(TrwTNeedagent trwTNeedagent,LandClient landClient);
    /**
     * 
    * @Title: getLandClientByFaciId 
    * @Description: 根据服务商id查询客源
    * @author haochen
    * @param @param page
    * @param @param param
    * @param @return  参数说明 
    * @return List<LandClient> 返回类型    
    * @throws 
    * @date 2018年8月16日 下午5:20:36
     */
	List<LandClient> getLandClientByFaciId(Page<LandClient> page, Map<String, String> param);
}
