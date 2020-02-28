package com.trw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTLookinfo;
import com.trw.vo.SoilSource;

import java.util.List;
import java.util.Map;

public interface LandMapper extends BaseMapper<TrwTLand> {

	/**
	 * 首页土地列表
	 */
	List<TrwTLand> selectMainLand(Page<TrwTLand> page, Map<String, String> param);

	/**
	 * 查供应或者需求的土地
	 */
	List<TrwTLand> findRequireLand(Page<TrwTLand> page, Map<String, String> map);

	/**
	 * 查服务商对应的土地信息
	 */
	List<TrwTLand> selectFaciLand(Page<TrwTLand> page, String faciid);

	/**
	 * 根据标签查询土地
	 *
	 * @param page
	 * @param landNote
	 * @return
	 */
	List<TrwTLand> selectLandNote(Page<TrwTLand> page, String landNote);

	/**
	 * 根据条件查询服务商对应的土地
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<TrwTLand> selectLandFaci(Page<TrwTLand> page, Map<String, String> map);

	/**
	 * 土地带看次数
	 * 
	 * @param page
	 * @param landNote
	 * @return
	 */
	List<TrwTLookinfo> selectLookModel(String productId);

	/**
	 * 我的土地管理
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<TrwTLand> selectMyLandsManage(Page<TrwTLand> page, Map<String, String> map);

	/**
	 * 查询我的土地详情
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<SoilSource> selectSourceDetails(Page<SoilSource> page, Map<String, String> map);

	/**
	 * 查询土地市场详情
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectLandMarket(Page<TrwTLand> page, Map<String, String> map);

	boolean allcalLand(Map<String, String> map);

	/**
	 * 设置带看费
	 * 
	 * @param map
	 */
	void updateLookModel(Map<String, String> map);

	/**
	 * 我的
	 * 
	 * @param map
	 * @return
	 */
	Boolean updateMyland(Map<String, String> map);

	/**
	 * * 设置带看次数后，更新土地带看模式
	 * 
	 * @param model
	 * @param productid
	 */
	void updateLookInfo(Map<String, String> map);

	/**
	 * 经纪人看到的土地市场
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectMarket(Page<TrwTLand> page, Map<String, String> map);

	/**
	 * 从土地市场申请土地,修改土地信息成为我的土地
	 * 
	 * @param map
	 * @return
	 */
	Boolean updateLandFormMarket(Map<String, String> map);

	/**
	 * 根据需求匹配合适的土地
	 * 
	 * @param page
	 * @param id
	 * @return
	 */
	List<TrwTLand> selectLandByNeedId(Page<TrwTLand> page, Map<String, String> map);

	/**
	 * 查询
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	List<TrwTLand> selectIndexLand(Page<TrwTLand> page, Map<String, Object> map);

	/**
	 * 
	 * @Title: selectAgentLandById
	 * @Description: 查询经纪人对应的土地(包括服务中心的管理)
	 * @author haochen
	 * @param @param
	 *            page
	 * @param @param
	 *            param
	 * @param @return
	 *            参数说明
	 * @return List<TrwTLand> 返回类型
	 * @throws @date
	 *             2018年8月20日 下午4:37:14
	 */
	List<TrwTLand> selectAgentLandById(Page<TrwTLand> page, Map<String, String> param);

	/**
	 * 
	 * @Title: selectLandTitle
	 * @Description: 查询已匹配经纪人的土地标题
	 * @author gongzhen
	 * @param @param
	 *            faciid
	 * @param @return
	 *            参数说明
	 * @return List<TrwTLand> 返回类型
	 * @throws @date
	 *             2018年8月20日
	 */
	List<TrwTLand> selectLandTitle(String faciid);

}
