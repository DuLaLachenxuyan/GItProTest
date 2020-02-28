package com.trw.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AreasMapper;
import com.trw.model.Areas;
import com.trw.service.IAreasService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 行政区域县区信息表 服务实现类
 * </p>
 *
 * @author 123
 * @since 2018-07-06
 */
@Service
public class AreasServiceImpl extends ServiceImpl<AreasMapper, Areas> implements IAreasService {
    
    @Override
    public List<Areas> selectBycityid(String id) {
        return baseMapper.selectBycityid(id);
    }
}
