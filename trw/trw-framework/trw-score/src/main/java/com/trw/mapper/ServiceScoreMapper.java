package com.trw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.ServiceScore;
import com.trw.vo.ScoreRank;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
public interface ServiceScoreMapper extends BaseMapper<ServiceScore> {
    
    List<ScoreRank> selectPageScoreRank();

}
