package com.trw.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.PfaFFarmers;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-07-18
 */
public interface PfaFFarmersMapper extends BaseMapper<PfaFFarmers> {
    
    List<PfaFFarmers> selectMin(Page<PfaFFarmers> page, Map<String, String> map);
    
    List<PfaFFarmers> selectRecommend();

}
