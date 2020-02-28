package com.trw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.BannerMapper;
import com.trw.model.TrwTBimg;
import com.trw.service.IBimgService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-11
 */
@Service
public class BimgServiceImpl extends ServiceImpl<BannerMapper, TrwTBimg> implements IBimgService {

	/**
	 * 查询所有轮播
	 */
	@Override
    public List<TrwTBimg> selectBanner() {
		return baseMapper.selectBanner();
	}


}
