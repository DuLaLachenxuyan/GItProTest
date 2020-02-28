package com.trw.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.Records;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-30
 */
public interface RecordsMapper extends BaseMapper<Records> {
    
    List<Records> selectBylandid(Page<Records> page, Map<String, String> map);
    
    Boolean updateRecords(Records records);

}
