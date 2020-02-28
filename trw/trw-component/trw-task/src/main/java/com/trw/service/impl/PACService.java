package com.trw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.PCAMapper;
import com.trw.service.IPCAService;
import com.trw.vo.Area;
import com.trw.vo.PCAVo;

@Service
public class PACService extends ServiceImpl<PCAMapper, PCAVo> implements IPCAService {

	@Override
	public List<PCAVo> selectPCA() {
		return baseMapper.selectPCA();
	}

	@Override
	public List<Area> getArea() {
		return baseMapper.getArea();
	}

	@Override
	public List<Area> getCity() {
		return baseMapper.getCity();
	}

	@Override
	public List<Area> getProvince() {
		return baseMapper.getProvince();
	}
}
