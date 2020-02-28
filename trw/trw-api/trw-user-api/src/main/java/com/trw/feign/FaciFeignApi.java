package com.trw.feign;

import com.trw.model.TrwTFaci;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "trw-user")
public interface FaciFeignApi {

	/**
	 * @Title: getFaciByLoc
	 * @Description: 根据区域查询加盟商信息
	 * @author luojing
	 * @param @param
	 *            location
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<TrwTFaci> 返回类型
	 * @throws @date
	 *             2018年7月4日
	 */
	@RequestMapping(value = "/getFaciByLoc",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<TrwTFaci> getFaciByLoc(@RequestParam("location") String location);

	@RequestMapping(value = "/selectFaciList",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<Map<String, Object>> selectFaciList(@RequestParam(required=false,value = "areaid") String areaid);

	/**
	 * @Title: getFaci
	 * @Description: 根据加盟商ID查询加盟商信息
	 * @author luojing
	 * @param @param
	 *            faci
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<TrwTFaci> 返回类型
	 * @throws @date
	 *             2018年7月4日
	 */
	@RequestMapping(value = "/getFaciByfaciId",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResultMsg<TrwTFaci> getFaciByfaciId(@RequestParam("faciId") String faciId);
}
