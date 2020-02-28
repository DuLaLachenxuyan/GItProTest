package com.trw.feign;

import com.trw.model.AgentDetail;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "trw-user")
public interface AgentDetailFeignApi {

	/**
	 * 
	 * @Title: updateAgentDetailById
	 * @Description: 更新经纪人详情的信息
	 * @author haochen
	 * @param @param
	 *            agentDetail
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<AgentDetail> 返回类型
	 * @throws @date
	 *             2018年7月25日 下午1:52:16
	 */
	@RequestMapping(value = "/updateAgentDetailById",method =RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<AgentDetail> updateAgentDetailById(@RequestBody AgentDetail agentDetail);

	/**
	 * 
	 * @Title: getAgentDetailById
	 * @Description: 查询经纪人详情的信息
	 * @author haochen
	 * @param @param
	 *            id
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<AgentDetail> 返回类型
	 * @throws @date
	 *             2018年7月25日 下午1:52:49
	 */
	@RequestMapping(value = "/getAgentDetailById",method =RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<AgentDetail> getAgentDetailById(@RequestParam("id") String id);
}
