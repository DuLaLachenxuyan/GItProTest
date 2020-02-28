package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTCallback;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-23
 */
public interface ITrwTCallbackService extends IService<TrwTCallback> {
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
    * @Description: 查看用户是否已经预约过这块地
    * @author haochen
    * @param @param param
    * @param @return  参数说明 
    * @return TrwTCallback 返回类型    
    * @throws 
    * @date 2018年7月12日 下午4:07:00
     */
	TrwTCallback findUserCall(Map<String, String> param);



}
