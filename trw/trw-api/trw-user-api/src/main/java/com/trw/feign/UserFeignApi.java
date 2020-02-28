package com.trw.feign;

import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.model.TrwTUser;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "trw-user")
public interface UserFeignApi {
	
	/**
	 * 根据id查询用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getUser",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<TrwTUser> getUser(@RequestParam("userId") String userId);

	/**
	 * @Title: getAccountById
	 * @Description: 根据ID查询
	 * @author luojing
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<TrwTAccount> 返回类型
	 * @throws @date
	 *             2018年7月4日
	 */
	@RequestMapping(value = "/guest/getAccountById",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<TrwTAccount> getAccountById(@RequestParam("userId") String userId);

	/**
	 * @Title: addAccount
	 * @Description: 插入资金账户
	 * @author luojing
	 * @param @param
	 *            acc
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月4日
	 */
	@RequestMapping(value = "/guest/addAccount",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<String> addAccount(@RequestBody TrwTAccount acc);

	/**
	 * @Title: addAccDetail
	 * @Description: 插入资金账户明细
	 * @author luojing
	 * @param @param
	 *            acc
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月4日
	 */
	@RequestMapping(value = "/guest/addAccDetail",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultMsg<String> addAccDetail(@RequestBody TrwTAccDetail acc);
	
	
	/**
	 * 查询收藏土地次数
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/getEnshrineNum",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<Integer> getEnshrineNum(@RequestParam("productId") String productId);

}
