package com.trw.mapper;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.FaciRecommend;
import com.trw.model.LandClient;
import com.trw.model.Recommend;
import com.trw.model.TrwTLand;
import com.trw.vo.NoMatchLand;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-28
 */
public interface RecommendMapper extends BaseMapper<Recommend> {
    /**
     * 推荐我的土地查询
     * @param page
     * @param map
     * @return
     */
    
    List<Recommend> selectRecommend(Page<Recommend> page, Map<String, String> map);

	List<Recommend> selectRecommendLand(Page<Recommend> page, Map<String, String> parm);
    
    List<FaciRecommend> selectFacitRecommend(Page<FaciRecommend> page, Map<String, String> map);
    
    Boolean updateByid(Recommend recommend);

	List<NoMatchLand> selectMatchLand(Map<String, String> parm);
    
    /**
     * 未推荐的土地
     * @param
     * @return
     */
    List<TrwTLand> selectUnrdedLand(Map<String,String> map);
    
    
    List<LandClient> selectMatchClient(Map<String, String> map);
    
    List<Recommend> getLandRecommend(Map<String, String> map);
    
    Boolean deleteByLandId(Map<String, String> map);
    
    List<Recommend> selectUnrdedRecommend(Map<String, String> map);
}
