package com.trw.util;

import com.trw.constant.Constant;
import com.trw.model.TrwTFaci;
import com.trw.service.impl.RedisService;
/**
 * 
 * @author jianglingyun
 */
public class BusinessUtil {
	private static RedisService redisService = SpringContextHolder.getBean(RedisService.class);
		
	/**
	 * 
	* @Title: getPlatFaci 
	* @Description: 获取平台信息
	* @author jianglingyun
	* @param @return  参数说明 
	* @return TrwTFaci 返回类型 
	* @throws 
	* @date 2018年8月6日
	 */
	public static TrwTFaci getPlatFaci() {
		TrwTFaci faci;
		faci = new TrwTFaci();
		String admin_mobile = redisService.getString(Constant.CONFIGPIX + "admin_mobile");
		String admin_name = redisService.getString(Constant.CONFIGPIX + "admin_name");
		faci.setContacts(admin_name);
		faci.setMobile(admin_mobile);
		return faci;
	}
}
