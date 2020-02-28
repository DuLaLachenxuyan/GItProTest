package com.trw.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.trw.constant.Constant;
import com.trw.service.impl.RedisService;

/**
 * 省市县转换
 * 
 * @author jianglingyun
 */
public class PCAConvert {
	private static RedisService redisService = SpringContextHolder.getBean(RedisService.class);

	private static Map<String, Object> provinces;
	private static Map<String, Object> citys;
	private static Map<String, Object> areas;
	
	private static PCAConvert pca = new PCAConvert();

	private void init() {
		// 初始化省
		provinces = JSONObject.parseObject(redisService.getString(Constant.PCAPIX + "Prov"));
		// 初始化市
		citys = JSONObject.parseObject(redisService.getString(Constant.PCAPIX + "city"));
		// 初始化县
		areas = JSONObject.parseObject(redisService.getString(Constant.PCAPIX + "area"));
	}
	
	public static PCAConvert me() {
		return pca;
	}
	
	private PCAConvert() {
		init();
	}

	/**
	 * 通过地区id获取中文
	 * 
	 * @param id
	 * @return
	 */
	public String convert(String id) {
		if (id.length() == 2) {
			return provinces.get(id).toString();
		}
		if (id.length() == 4) {
			return citys.get(id).toString();
		}
		if (id.length() == 6) {
			if(citys.get(id)!=null) {
				return citys.get(id).toString();
			}else {
				return areas.get(id).toString();
			}
		}
		return id;
	}


}
