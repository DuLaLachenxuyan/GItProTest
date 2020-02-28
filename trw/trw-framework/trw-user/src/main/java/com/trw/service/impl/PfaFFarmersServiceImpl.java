package com.trw.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.PfaFFarmersMapper;
import com.trw.model.PfaFFarmers;
import com.trw.service.IPfaFFarmersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-07-18
 */
@Service
public class PfaFFarmersServiceImpl extends ServiceImpl<PfaFFarmersMapper, PfaFFarmers> implements IPfaFFarmersService {
    
    @Override
    public List<PfaFFarmers> selectMin(Page<PfaFFarmers> page, Map<String, String> map) {
        return baseMapper.selectMin(page,map);
    }
    
    @Override
    public List<PfaFFarmers> selectRecommend() {
        return baseMapper.selectRecommend();
    }
}
