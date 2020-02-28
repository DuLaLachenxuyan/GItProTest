package com.trw.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTLookinfo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author haochen
 * @since 2018-07-2
 */
public interface LookinfoMapper extends BaseMapper<TrwTLookinfo> {
	/**
	 * 
	 * @Title: insertList
	 * @Description: 批量插入数据
	 * @author haochen
	 * @param @param
	 *            listLookinfo
	 * @param @return
	 *            参数说明
	 * @return int 返回类型
	 * @throws @date
	 *             2018年7月11日 下午1:52:45
	 */
	int insertList(List<TrwTLookinfo> listLookinfo);

	/**
	 * 
	 * @Title: selectListById
	 * @Description: 根据土地的id查看带看费和次数
	 * @author haochen
	 * @param @param
	 *            productid
	 * @param @return
	 *            参数说明
	 * @return List<TrwTLookinfo> 返回类型
	 * @throws @date
	 *             2018年7月11日 下午2:18:47
	 */
	List<TrwTLookinfo> selectListById(String productid);

}
