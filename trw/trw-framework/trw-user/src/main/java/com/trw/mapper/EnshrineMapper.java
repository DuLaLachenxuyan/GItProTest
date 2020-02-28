package com.trw.mapper;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.PlatTEnshrine;
import com.trw.vo.Enshrine;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
public interface EnshrineMapper extends BaseMapper<PlatTEnshrine> {
    
    /**
     * 收藏页面
     * @param platTEnshrine
     * @return
     */
    List<Enshrine>  selectPTS(Page<PlatTEnshrine> page, Map<String, Object> map);
    /**
     * 
    * @Title: findLandClient 
    * @Description: 查询收藏的客户需求土地 
    * @author haochen
    * @param @param page
    * @param @param param
    * @param @return  参数说明 
    * @return List<Map<String,Object>> 返回类型    
    * @throws 
    * @date 2018年8月15日 下午1:51:49
     */
	List<Map<String, Object>> findLandClient(Page<PlatTEnshrine> page, Map<String, String> param);
    
    
    
    
    
    
}
