package com.trw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTNeedagent;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-29
 */
public interface NeedagentMapper extends BaseMapper<TrwTNeedagent> {

	boolean insertIn(TrwTNeedagent entiry);

}
