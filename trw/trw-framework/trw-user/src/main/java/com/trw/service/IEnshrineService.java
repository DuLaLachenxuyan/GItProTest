package com.trw.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.PlatTEnshrine;
import com.trw.vo.Enshrine;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
public interface IEnshrineService extends IService<PlatTEnshrine> {
    /**
     * 查询收藏信息
     * @param page
     * @param map
     * @return
     */
    public List<Enshrine>  selectPTS(Page<PlatTEnshrine> page, Map<String, Object> map);
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
    * @date 2018年8月15日 下午1:50:23
     */
	public List<Map<String, Object>> findLandClient(Page<PlatTEnshrine> page, Map<String, String> param);
    
    
    
    
}
