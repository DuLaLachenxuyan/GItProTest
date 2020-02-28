package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTDeal;
import com.trw.vo.DealDetail;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author haochen123
 * @since 2018-06-30
 */
public interface DealMapper extends BaseMapper<TrwTDeal> {
	/**
	 * 
	 * @Title: findDeal
	 * @Description: 查看服务中心的交易
	 * @author haochen
	 * @param @param
	 *            param
	 * @param @param
	 *            page
	 * @param @return
	 *            参数说明
	 * @return List<TrwTDeal> 返回类型
	 * @throws @date
	 *             2018年7月4日 下午3:52:57
	 */
	List<TrwTDeal> findDeal(Map<String, String> param, Page<TrwTDeal> page);
    /**
     * 
    * @Title: getDetail 
    * @Description: 查询交易详情
    * @author haochen
    * @param @param id
    * @param @return  参数说明 
    * @return DealDetail 返回类型    
    * @throws 
    * @date 2018年8月17日 上午11:26:10
     */
	DealDetail getDetail(String dealid);

}
