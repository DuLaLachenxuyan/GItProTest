package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTMsg;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
public interface MsgMapper extends BaseMapper<TrwTMsg> {
	/**
	 * 查询消息通知
	 */
	List<TrwTMsg> findMyMsg(Page<TrwTMsg> page, Map<String, String> param);

}
