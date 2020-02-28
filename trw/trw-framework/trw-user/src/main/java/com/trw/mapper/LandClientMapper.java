package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.LandClient;
import com.trw.vo.ClientVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-14
 */
public interface LandClientMapper extends BaseMapper<LandClient> {

	List<LandClient> selectLandClient(Page<LandClient> page, Map<String, String> param);

	List<LandClient> selectByUserid(String userId, Page<LandClient> page);

	List<LandClient> selectByTerm(Page<LandClient> page, Map<String, String> param);

	List<ClientVo> selectClient(Page<LandClient> page, Map<String, String> param);
	
	Boolean updateAgentId(Map<String, String> map);
	
	List<LandClient> selectSimilar(Page<LandClient> page,Map<String, String> map);
	
	List<LandClient> selectTrwClient(String id);
	
	List<LandClient> selectAgentClient(String agentid);
	
	List<LandClient> selectMatched(String id);
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
