package com.trw.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceScoreMapper;
import com.trw.model.ServiceScore;
import com.trw.service.ServiceScoreService;
import com.trw.vo.ScoreRank;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
@Service
public class ServiceScoreServiceImpl extends ServiceImpl<ServiceScoreMapper, ServiceScore> implements ServiceScoreService {
    
    @Override
    public List<ScoreRank> selectPageScoreRank() {
        return baseMapper.selectPageScoreRank();
    }
}
