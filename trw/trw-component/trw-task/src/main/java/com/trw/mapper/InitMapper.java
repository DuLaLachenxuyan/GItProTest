package com.trw.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTFaci;

public interface InitMapper extends BaseMapper<TrwTFaci>{
	/**
	 * 初始化服务中心
	 */
	public List<TrwTFaci> findFaci();
}
