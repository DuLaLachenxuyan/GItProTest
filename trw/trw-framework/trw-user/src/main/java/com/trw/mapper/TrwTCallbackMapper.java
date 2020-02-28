package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTCallback;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-23
 */
public interface TrwTCallbackMapper extends BaseMapper<TrwTCallback> {
	/**
	 * 按条件查询预约回电
	 */
	List<Map<String, Object>> findCallback(Map<String, String> param, Page<TrwTCallback> page);

	/**
	 * 修改预约回电信息
	 */
	int updateCallback(Map<String, String> param);

	/**
	 * 
	 * @Title: findUserCall
	 * @Description: 查看用户是否预约过这块土地
	 * @author haochen
	 * @param @param
	 *            param
	 * @param @return
	 *            参数说明
	 * @return TrwTCallback 返回类型
	 * @throws @date
	 *             2018年7月12日 下午4:12:28
	 */
	TrwTCallback findUserCall(Map<String, String> param);

}
