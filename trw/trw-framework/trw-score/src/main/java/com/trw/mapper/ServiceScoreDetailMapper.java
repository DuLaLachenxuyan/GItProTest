package com.trw.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.ServiceScoreDetail;
import com.trw.vo.ScoreDetailRule;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
public interface ServiceScoreDetailMapper extends BaseMapper<ServiceScoreDetail> {
    
    List<ScoreDetailRule> selectByFaciId(Page<ScoreDetailRule> page,String faciid);

}
