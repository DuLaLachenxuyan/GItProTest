package com.trw.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trw.constant.Constant;
import com.trw.service.impl.RedisService;
import com.trw.util.IdGenerate;
import com.trw.vo.TokenData;

public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected RedisService redisService;
	
	@Autowired
	protected IdGenerate idGenerate;
	
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	
	/**
	* @Title: getTokenData 
	* @Description: 获取Token
	* @author luojing
	* @param @return  参数说明 
	* @return TokenData 返回类型 
	* @throws 
	* @date 2018年7月9日
	 */
	protected TokenData getTokenData() {
		return (TokenData) request.getAttribute("tokenData");
	}
	
	protected Map<String,String> getParamMapFromRequest(HttpServletRequest req){
		Map<String,String> params = new HashMap<>();
		Map<String,String[]> properties = req.getParameterMap();
		for(String key :properties.keySet()){
			String value = "";
			Object valueObj =properties.get(key);
			if (null == properties.get(key)) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value += values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj + "";
			}
			params.put(key, value);
			
		}
		return params;
	}
	
	/**
     * 验证短信
     * @param phone
     * @param code
     * @return
     */
	protected boolean validSmsCode(String phone, String code){
    	String sendsms =redisService.getString(Constant.CONFIGPIX+"sendsms");
    	if(!Constant.YES.equals(sendsms)){//不校验短信
    		return true;
    	}
    	
        //取出所有有关该手机号的短信验证码
    	String sms= redisService.getString(phone);
        if(sms==null){
        	logger.error("短信验证失败");
            return false;
        }
        if (sms.equals(code)){
            //删除掉该redis
        	redisService.remove(phone);
            return true;
        }
        return false;
    }
}
