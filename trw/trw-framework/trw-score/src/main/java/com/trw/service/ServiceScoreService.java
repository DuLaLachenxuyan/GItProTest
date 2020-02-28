package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceScore;
import com.trw.vo.ScoreRank;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
public interface ServiceScoreService extends IService<ServiceScore> {
    /**
     * 查询积分排行
     *
     * @return
     */
    List<ScoreRank> selectPageScoreRank();
    
}
