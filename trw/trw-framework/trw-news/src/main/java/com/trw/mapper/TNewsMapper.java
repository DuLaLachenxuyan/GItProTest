package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TNews;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
public interface TNewsMapper extends BaseMapper<TNews> {
    List<TNews> selectMainNews();
    
    List<TNews> selectImage();
    
    List<TNews> selectNews(String newsNote);
    
    List<TNews> selectHsbNews(Map<String, String> map);

	List<TNews> selectNewsHome();

	List<TNews> selectNewsType(Page<TNews> page, String typeId);

}
