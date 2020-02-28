package com.trw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.service.IPCAService;
import com.trw.vo.Area;
import com.trw.vo.PCAVo;

import io.swagger.annotations.ApiOperation;

@RestController
public class PCAController {
	
	@Autowired
	private IPCAService pcaService;
	
	@RequestMapping(value = "/getPCA")
	@ApiOperation(httpMethod = "POST",value = "生成省市县数据", notes = "生成省市县数据") 
	public List<PCAVo> getPCA(HttpServletRequest req) {
		List<PCAVo> data =pcaService.selectPCA();
		return data;
	}
	
	@RequestMapping(value = "/getProvinces")
	@ApiOperation(httpMethod = "POST",value = "省", notes = "省") 
	public List<Area> getProvinces(HttpServletRequest req) {
		List<Area> provinces = pcaService.getProvince();
		return provinces;
		
	}
	@RequestMapping(value = "/getCitys")
	@ApiOperation(httpMethod = "POST",value = "市", notes = "市") 
	public List<Area> getCitys(HttpServletRequest req) {
		List<Area> provinces = pcaService.getCity();
		return provinces;
	}
	@RequestMapping(value = "/getArea")
	@ApiOperation(httpMethod = "POST",value = "县", notes = "县") 
	public List<Area> getArea(HttpServletRequest req) {
		List<Area> provinces = pcaService.getArea();
		return provinces;
		
	}
  
}
