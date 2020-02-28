package com.trw.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.RecordsMapper;
import com.trw.model.Records;
import com.trw.service.RecordsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-30
 */
@Service
public class RecordsServiceImpl extends ServiceImpl<RecordsMapper, Records> implements RecordsService {
    
    @Override
    public List<Records> selectBylandid(Page<Records> page, Map<String, String> map) {
        return baseMapper.selectBylandid(page,map);
    }
    
    @Override
    public Boolean updateRecords(Records records) {
        return baseMapper.updateRecords(records);
    }
}
