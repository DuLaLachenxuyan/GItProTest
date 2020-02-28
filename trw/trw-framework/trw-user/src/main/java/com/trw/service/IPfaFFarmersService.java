package com.trw.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.PfaFFarmers;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-07-18
 */
public interface IPfaFFarmersService extends IService<PfaFFarmers> {
    
    
    /**
     * 大国农匠首页农匠信息
     * @param page
     * @param map
     * @return
     */
    List<PfaFFarmers> selectMin(Page<PfaFFarmers> page, Map<String,String> map);
    
    /**
     * 大国农匠首页推荐的农匠的信息
     */
    List<PfaFFarmers> selectRecommend();

}
