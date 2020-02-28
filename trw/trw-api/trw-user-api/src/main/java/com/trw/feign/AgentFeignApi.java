package com.trw.feign;

import com.trw.model.TrwTAgent;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 14:06 2018/7/5
 * @Modified By:
 */
@FeignClient(value = "trw-user")
public interface AgentFeignApi {

	/**
	 * 通过id查询经纪人详情
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getAgentById",method=RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<TrwTAgent> getAgentById(@RequestParam("id") String id);

	/**
	 * 
	 * @Title: updateAgent
	 * @Description: 修改经纪人的信息
	 * @author haochen
	 * @param @param
	 *            agent
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<TrwTAgent> 返回类型
	 * @throws @date
	 *             2018年7月25日 上午9:17:37
	 */
	@RequestMapping(value = "/updateAgentById",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<TrwTAgent> updateAgentById(@RequestBody TrwTAgent agent);

}
