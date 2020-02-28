package com.trw.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.Areas;

import java.util.List;

/**
 * <p>
 * 行政区域县区信息表 Mapper 接口
 * </p>
 *
 * @author 123
 * @since 2018-07-06
 */
public interface AreasMapper extends BaseMapper<Areas> {
    
    /**
     * 根据城市查询下属的区县
     * @Description:
     * @Author: LinHai
     * @Date: 16:16 2018/7/6
     */
    List<Areas> selectBycityid(String id);

}
