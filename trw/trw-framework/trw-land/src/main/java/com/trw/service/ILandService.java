package com.trw.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.Records;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTLookinfo;
import com.trw.vo.SoilSource;

import java.util.List;
import java.util.Map;


public interface ILandService extends IService<TrwTLand> {
    /**
     * 主页查询土地
     *
     * @param map
     * @return
     */
    List<TrwTLand> selectMainLand(Page<TrwTLand> page, Map<String, String> map);
    
    /**
     * 根据服务商查询相应的土地信息
     *
     * @param page
     * @param
     * @return
     */
    List<TrwTLand> selectFaciLand(Page<TrwTLand> page,String faciid);
    
    
    /**
     * 根据标签查询土地
     *
     * @param page
     * @param landNote
     * @return
     */
    List<TrwTLand> selectLandNote(Page<TrwTLand> page, String landNote);
    
    
    /**
     * 根据条件查询服务商中的土地
     * @param page
     * @param map
     * @return
     */
    List<TrwTLand> selectLandFaci(Page<TrwTLand> page,Map<String,String> map);
    
    /**
     * 土地带看次数
     * @param productId
     * @return
     */
	List<TrwTLookinfo> selectLookModel(String productId);
	/**
	 * 获取土地信息
	 * @return
	 */
	TrwTLand getLandInfo(String productid);
    
    
    /**
     * 添加土地
     * @param land
     * @return
     */
	boolean saveLand(TrwTLand land);
    
    /**
     * 查询我的土地管理
     * @param page
     * @param map
     * @return
     */
    List<TrwTLand> selectMyLandsManage(Page<TrwTLand> page,Map<String,String> map);


    
    /**
     * 查询土源详情
     * @param page
     * @param map
     * @return
     */
    List<SoilSource> selectSourceDetails(Page<SoilSource> page, Map<String,String> map);
    
    /**
     * 查询市场土地
     * @param page
     * @param map
     * @return
     */
    List<Map<String, Object>> selectLandMarket(Page<TrwTLand> page, Map<String,String> map);
    
    
    /**
     * 查询经纪人的土地市场
     * @param page
     * @param map
     * @return
     */
    List<Map<String, Object>> selectMarket(Page<TrwTLand> page, Map<String,String> map);
    /**
     * 土地分配
    * @Title: allcalLand 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @author jianglingyun
    * @param @param productid
    * @param @param allocated  参数说明 
    * @return void 返回类型 
    * @throws 
    * @date 2018年7月18日
     */
    void allcalLand(String productid, String allocated);
    
    /**
     * 编辑我的土地
     * @param map
     * @return
     */
    Boolean updateMyland(Map<String,String> map);
    /**
     * 从土地市场申请土地,修改土地信息成为我的土地
     * @param map
     * @return
     */
    Boolean updateLandFormMarket(Map<String,String> map);
    
    /**
     * 查询跟需求匹配的土地
     * @param page
     * @param id
     * @return
     */
    List<TrwTLand> selectLandByNeedId(Page<TrwTLand> page,Map<String,String> map);
    
    
    /**
     * 服务商发布土地
     * @param land
     * @param trwTAllocation
     * @return
     */
    Boolean addLand(TrwTLand land, TrwTAllocation trwTAllocation);
    
    
    /**
     * 首页查询土地
     * @param page
     * @param map
     * @return
     */
    List<TrwTLand> selectIndexLand(Page<TrwTLand> page,Map<String,Object> map);
    /**
     * 
    * @Title: selectAgentLandById 
    * @Description: 查询经纪人对应的土地(包括服务中心的管理)
    * @author haochen
    * @param @param page
    * @param @param param
    * @param @return  参数说明 
    * @return List<TrwTLand> 返回类型    
    * @throws 
    * @date 2018年8月20日 下午4:37:14
     */
	List<TrwTLand> selectAgentLandById(Page<TrwTLand> page, Map<String, String> param);
    
    /**
     * 
    * @Title: selectLandTitle 
    * @Description: 查询已匹配经纪人的土地标题
    * @author gongzhen
    * @param @param faciid
    * @param @return  参数说明 
    * @return List<TrwTLand> 返回类型 
    * @throws 
    * @date 2018年8月20日
     */
	List<TrwTLand> selectLandTitle(String faciid);
    
    /**
     * 土地审核
     * @param land
     * @param records
     * @return
     */
    Boolean addLandApproval(TrwTLand land, Records records);
    
}
