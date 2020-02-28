package com.trw.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.vo.Area;
import com.trw.vo.PCAVo;

public interface PCAMapper extends BaseMapper<PCAVo>{

	List<PCAVo> selectPCA();
	/**
	 * 获取省
	 * @return
	 */
	List<Area> getProvince();
	/**
	 * 获取市
	 * @return
	 */
	List<Area> getCity();
	/**
	 * 获取县区
	 * @return
	 */
	List<Area> getArea();
	
	

}
