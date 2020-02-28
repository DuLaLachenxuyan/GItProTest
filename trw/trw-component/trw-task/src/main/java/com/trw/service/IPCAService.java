package com.trw.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.trw.vo.Area;
import com.trw.vo.PCAVo;

public interface IPCAService  extends IService<PCAVo> {

	List<PCAVo> selectPCA();

	List<Area> getArea();

	List<Area> getCity();

	List<Area> getProvince();

}
