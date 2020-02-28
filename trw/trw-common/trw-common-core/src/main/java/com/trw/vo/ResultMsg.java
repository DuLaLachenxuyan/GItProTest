package com.trw.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ResultMsg", description = "返回对象")
public class ResultMsg<T> implements Serializable{
	
	private static final long serialVersionUID = 8367394632682088074L;

	@ApiModelProperty(value = "状态码")
	private String code;
	
	@ApiModelProperty(value = "消息")
	private String msg;
	
	private T data;
	
	@ApiModelProperty(value = "token")
	private String token;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
