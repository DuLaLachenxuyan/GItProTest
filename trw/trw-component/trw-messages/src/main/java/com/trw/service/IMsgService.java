package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTMsg;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
public interface IMsgService extends IService<TrwTMsg> {
	/**
	 *  查询消息通知
	 *   */
	List<TrwTMsg> findMyMsg(Page<TrwTMsg> page, Map<String, String> param);

}
