package com.trw.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.RecommendMapper;
import com.trw.model.FaciRecommend;
import com.trw.model.LandClient;
import com.trw.model.Recommend;
import com.trw.model.TrwTLand;
import com.trw.service.RecommendService;
import com.trw.vo.NoMatchLand;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-28
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {
    
    @Override
    public List<Recommend> selectRecommend(Page<Recommend> page, Map<String, String> map) {
        return baseMapper.selectRecommend(page,map);
    }

	@Override
	public List<Recommend> selectRecommendLand(Page<Recommend> page, Map<String, String> parm) {
		return baseMapper.selectRecommendLand(page,parm);
	}
    @Override
    public List<FaciRecommend> selectFacitRecommend(Page<FaciRecommend> page, Map<String, String> map) {
        return baseMapper.selectFacitRecommend(page,map);
    }
    
    @Override
    public Boolean updateByid(Recommend recommend) {
        return baseMapper.updateByid(recommend);
    }

	@Override
	public List<NoMatchLand> selectMatchLand(Map<String, String> parm) {
		return baseMapper.selectMatchLand(parm);
	}

    @Override
    public List<TrwTLand> selectUnrdedLand(Map<String,String> map) {
        return baseMapper.selectUnrdedLand(map);
    }
    
    @Override
    public List<LandClient> selectMatchClient(Map<String, String> map) {
        return baseMapper.selectMatchClient(map);
    }
    
    @Override
    public List<Recommend> getLandRecommend(Map<String, String> map) {
        return baseMapper.getLandRecommend(map);
    }
    
    @Override
    @Transactional(readOnly=false)
    public void insertData(Map<String, String> map, Recommend recommend) {
        //删除推荐过期的土地
        deleteByLandId(map);
        insert(recommend);
    }
    
    @Override
    public Boolean deleteByLandId(Map<String, String> map) {
        return baseMapper.deleteByLandId(map);
    }
    
    @Override
    public List<Recommend> selectUnrdedRecommend(Map<String, String> map) {
        return baseMapper.selectUnrdedRecommend(map);
    }
    
    
}
