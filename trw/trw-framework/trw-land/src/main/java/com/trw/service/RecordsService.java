package com.trw.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.Records;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-30
 */
public interface RecordsService extends IService<Records> {
    /**
     * 根据土地id查询根据记录
     * @param map
     * @return
     */
    List<Records> selectBylandid(Page<Records> page,Map<String, String> map);
    
    /**
     * 修改跟进记录
     * @param records
     * @return
     */
    Boolean updateRecords(Records records);

}
