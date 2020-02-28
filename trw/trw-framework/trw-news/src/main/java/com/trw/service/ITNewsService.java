package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TNews;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
public interface ITNewsService extends IService<TNews> {
    
    /**
     * 查询新闻资讯
     * @param page
     * @return
     */
    List<TNews> selectMainNews();
    
    /**
     * 通过标签查询新闻资讯
     * @param page
     * @param newsNote
     * @return
     */
    List<TNews> selectNews(String newsNote);
    
    /**
     * 首页搜索
     * @param page
     * @param map
     * @return
     */
    List<TNews> selectHsbNews(Map<String,String> map);
    /**
     * 新闻首页查询
    * @Title: selectNewsHome 
    * @Description:  新闻首页查询
    * @author jianglingyun
    * @param @return  参数说明 
    * @return List<TNews> 返回类型 
    * @throws 
    * @date 2018年7月9日
     */
	List<TNews> selectNewsHome();
	/**
	 * 分类查询新闻列表
	* @Title: selectNewsType 
	* @Description: 分类查询新闻列表
	* @author jianglingyun
	* @param @param typeId
	* @param @return  参数说明 
	* @return List<TNews> 返回类型 
	* @throws 
	* @date 2018年7月10日
	 */
	List<TNews> selectNewsType(Page<TNews> page,String typeId);

}
