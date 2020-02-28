package com.trw.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.constant.Constant;
import com.trw.mapper.ServiceScoreDetailMapper;
import com.trw.model.ServiceScore;
import com.trw.model.ServiceScoreDetail;
import com.trw.service.ServiceScoreDetailService;
import com.trw.service.ServiceScoreService;
import com.trw.vo.ScoreDetailRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
@Service
public class ServiceScoreDetailServiceImpl extends ServiceImpl<ServiceScoreDetailMapper, ServiceScoreDetail>
    implements ServiceScoreDetailService {
    
    @Autowired
    private ServiceScoreService scoreService;
    
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public boolean insertDetail(ServiceScoreDetail entity) {
        // 添加积分明细操作
        boolean insert = insert(entity);
        // 操作服务商总积分
        ServiceScore faciScore = scoreService.selectById(entity.getFaciid());
        Integer totalscore = faciScore.getTotalscore();// 服务商总积分
        if (Constant.INTTPUT.equals(entity.getType())) {
            faciScore.setTotalscore(totalscore + entity.getScore());
        } else if (Constant.OUTPUT.equals(entity.getType())) {
            faciScore.setTotalscore(totalscore - entity.getScore());
        }
        boolean flg = scoreService.updateById(faciScore);
        if (flg && insert) {
            return true;
        }
        return false;
    }
    
    @Override
    public List<ScoreDetailRule> selectByFaciId(Page<ScoreDetailRule> page,String faciid) {
        return baseMapper.selectByFaciId(page,faciid);
    }
    
}
