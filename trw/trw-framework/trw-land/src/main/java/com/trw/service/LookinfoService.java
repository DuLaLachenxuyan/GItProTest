package com.trw.service;


import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTLookinfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-22
 */
public interface LookinfoService extends IService<TrwTLookinfo> {
    
    /*
     * * 设置带看费
     * @param productid
     * @param lookinfos
     */
	void insertData(String productid, List<TrwTLookinfo> lookinfos,String deposit);
    
    

}
