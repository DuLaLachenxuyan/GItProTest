package com.trw.feign;

import com.trw.model.LandClient;
import com.trw.vo.ResultMsg;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "trw-user")
public interface CustomerFeignApi {
	/**
	 * 
	 * @Title: landClient
	 * @Description: 用户发布寻找土地需求
	 * @author gongzhen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg 返回类型
	 * @throws @date
	 *             2018年7月3日
	 */
	@RequestMapping(value = "/guest/postClient",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<LandClient> postClient(HttpServletRequest req);

	/**
	 * 
	 * @Title: getLandClient
	 * @Description: 土地流转土地需求列表查询
	 * @author gongzhen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg 返回类型
	 * @throws @date
	 *             2018年7月3日
	 */
	@RequestMapping(value = "/getLandClient",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<List<LandClient>> getLandClient(HttpServletRequest req);

	/**
	 * 根据id查询需求
	 * 
	 * @Title: getClientById
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author gongzhen
	 * @param @param
	 *            needid
	 * @param @return
	 *            参数说明
	 * @return ResultMsg 返回类型
	 * @throws @date
	 *             2018年7月9日
	 */
	@RequestMapping(value = "/getClientById",method=RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<LandClient> getClientById(@RequestParam("needid") String needid);

	/**
	 * 根据土地id查询已经匹配的客户
	 * 
	 * @param productid
	 * @return
	 */
	@RequestMapping(value = "/selectMatched",method=RequestMethod.GET,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<List<LandClient>> selectMatched(@RequestParam("productid") String productid);

	/**
	 * 
	 * @Title: updateClientById
	 * @Description: 修改客源信息
	 * @author haochen
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<LandClient> 返回类型
	 * @throws @date
	 *             2018年7月28日 上午11:19:36
	 */
	@RequestMapping(value = "/updateClientById",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<LandClient> updateClientById(@RequestBody LandClient landClient);
}
