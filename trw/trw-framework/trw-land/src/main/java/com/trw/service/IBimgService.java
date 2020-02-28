package com.trw.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTBimg;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-11
 */
public interface IBimgService extends IService<TrwTBimg> {
	//查询轮播
	public List<TrwTBimg> selectBanner();
}
