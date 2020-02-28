package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.Areas;

import java.util.List;


/**
 * <p>
 * 行政区域县区信息表 服务类
 * </p>
 *
 * @author 123
 * @since 2018-07-06
 */
public interface IAreasService extends IService<Areas> {
    /**
     * 根据城市查询下面的县
     * @Description:
     * @Author: LinHai
     * @Date: 16:15 2018/7/6
     */
    List<Areas> selectBycityid(String id);

}
