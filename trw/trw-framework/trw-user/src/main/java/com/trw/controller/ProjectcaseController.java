package com.trw.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.trw.constant.Constant;
import com.trw.model.Projectcase;
import com.trw.service.ProjectcaseService;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  项目案例controller
 * </p>
 *
 * @author gongzhen123
 * @since 2018-08-16
 */
@RestController
public class ProjectcaseController {
	@Autowired
	private ProjectcaseService projectcaseService;
	/**
	 * 
	* @Title: getProjectcase 
	* @Description: 获取服务下的经典案例
	* @author gongzhen
	* @param @param faciid
	* @param @return  参数说明 
	* @return ResultMsg<TrwTAgent> 返回类型 
	* @throws 
	* @date 2018年8月16日
	 */
	@RequestMapping(value = "/getProjectcase", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "根据服务商id获取经典案例", notes = "根据服务商id获取经典案例")
	public ResultMsg<List<Projectcase>> getProjectcase() {
		ResultMsg<List<Projectcase>> result = new ResultMsg<List<Projectcase>>();
		Wrapper<Projectcase> wrapper = new EntityWrapper<Projectcase>();
		List<Projectcase> list = projectcaseService.selectList(wrapper);
		if (list==null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("没有查询到经典案例");
			return result;
		}
		result.setCode(Constant.CODE_SUCC);
		result.setData(list);
		return result;
	}
}

