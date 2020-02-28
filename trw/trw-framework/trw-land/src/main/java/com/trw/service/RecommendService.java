package com.trw.service;


import com.baomidou.mybatisplus.service.IService;
import com.trw.model.FaciRecommend;
import com.trw.model.LandClient;
import com.trw.model.Recommend;
import com.trw.model.TrwTLand;
import com.trw.vo.NoMatchLand;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-28
 */
public interface RecommendService extends IService<Recommend> {
    
    /**
     * 查询推荐土地
     */
    List<Recommend> selectRecommend(Page<Recommend> page, Map<String, String> map);

	List<Recommend> selectRecommendLand(Page<Recommend> page, Map<String, String> parm);

    /**
     * 查询服务商管理的推荐土地
     * @param page
     * @param map
     * @return
     */
    List<FaciRecommend> selectFacitRecommend(Page<FaciRecommend> page, Map<String,String> map);
    
    
    Boolean updateByid(Recommend recommend);
    
    /**
     * 
    * @Title: selectMatchLand 
    * @Description: 查询未被匹配的土地
    * @author gongzhen
     * @param parm 
    * @param @param faciid
    * @param @param needid
    * @param @return  参数说明 
    * @return List<NoMatchLand> 返回类型 
    * @throws 
    * @date 2018年7月31日
     */

	List<NoMatchLand> selectMatchLand(Map<String, String> parm);
    
    /**
     * 查询未推荐的土地
     * @param id
     * @return
     */
   List<TrwTLand> selectUnrdedLand(Map<String,String> map);
    
    /**
     * 查询未匹配过的客户
     * @param map
     * @return
     */
   List<LandClient> selectMatchClient(Map<String,String> map);
    
    /**
     * 查询土地是否推荐过
     * @param id
     * @return
     */
   List<Recommend> getLandRecommend(Map<String, String> map);
    
    /**
     * 新增推荐的土地
     * @param map
     * @param recommend
     */
    void insertData(Map<String,String> map,Recommend recommend);
    
    /**
     * 删除过期的推荐土地
     * @param map
     * @return
     */
    Boolean deleteByLandId(Map<String,String> map);
    
    /**
     * 查询在该土地是否已经在该推荐区域上
     * @param map
     * @return
     */
    List<Recommend> selectUnrdedRecommend(Map<String,String> map);
    
}
