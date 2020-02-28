package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.TNewsMapper;
import com.trw.model.TNews;
import com.trw.service.ITNewsService;

/**
 * <p>
 *  新闻
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
@Service
public class TNewsServiceImpl extends ServiceImpl<TNewsMapper, TNews> implements ITNewsService {
    
    @Override
    public List<TNews> selectMainNews() {
        return baseMapper.selectMainNews();
    }
    
    @Override
    public List<TNews> selectNews(String newsNote) {
        return baseMapper.selectNews(newsNote);
    }
    
    @Override
    public List<TNews> selectHsbNews(Map<String, String> map) {
        return baseMapper.selectHsbNews(map);
    }

	@Override
	public List<TNews> selectNewsHome() {
		return baseMapper.selectNewsHome();
	}

	@Override
	public List<TNews> selectNewsType(Page<TNews> page,String typeId) {
		return baseMapper.selectNewsType(page,typeId);
	}
}
