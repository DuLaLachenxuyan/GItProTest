package com.trw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.model.TrwTBimg;
import com.trw.service.IBimgService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BannerController {
	
	@Autowired
	private IBimgService bimgService;
	
	/**
	 * 查询banner轮播
	* @Title: selectBanner 
	* @Description: 查询banner轮播
	* @author jianglingyun
	* @param @return  参数说明 
	* @return List<TrwTBimg> 返回类型 
	* @throws 
	* @date 2018年7月3日
	 */
	@RequestMapping("/selectBanner")
	@ApiOperation(httpMethod = "POST", value = "查询banner轮播", notes = "查询banner轮播")
	public List<TrwTBimg> selectBanner(){
		return bimgService.selectBanner();
		
	}

}
