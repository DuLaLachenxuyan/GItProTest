package com.trw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trw.constant.Constant;
import com.trw.model.AgentDetail;
import com.trw.service.AgentDetailService;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 经纪人明细表 前端控制器
 * </p>
 *
 * @author gongzhen123
 * @since 2018-07-25
 */
@RestController
public class AgentDetailController {

	@Autowired
	private AgentDetailService agentDetailService;

	/**
	 * 
	* @Title: getAgentDetailById 
	* @Description: 查看经纪人明细(模块调用的方法)
	* @author haochen
	* @param @param id
	* @param @return  参数说明 
	* @return ResultMsg<AgentDetail> 返回类型    
	* @throws 
	* @date 2018年8月18日 下午4:21:39
	 */
	
	
	@RequestMapping(value = "/getAgentDetailById", method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(value = "查看经纪人明细", notes = "查看经纪人明细")
	public ResultMsg<AgentDetail> getAgentDetailById(@RequestParam("id") String id) {
		ResultMsg<AgentDetail> resultMsg = new ResultMsg<>();
		AgentDetail agentDetail = agentDetailService.selectById(id);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(agentDetail);
		return resultMsg;
	}

	/**
	 * 
	 * @Title: updateAgentDetailById
	 * @Description: 更新经纪人明细   (模块调用的方法)
	 * @author haochen
	 * @param @param
	 *            agentDetail
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<AgentDetail> 返回类型
	 * @throws @date
	 *             2018年8月18日 下午4:19:46
	 */
	@RequestMapping(value = "/updateAgentDetailById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "更新经纪人明细", notes = "更新经纪人明细")
	public ResultMsg<AgentDetail> updateAgentDetailById(@RequestBody AgentDetail agentDetail) {
		ResultMsg<AgentDetail> resultMsg = new ResultMsg<>();
		if (agentDetailService.updateById(agentDetail)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(agentDetail);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		return resultMsg;
	}
}
