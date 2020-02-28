package com.trw.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "短信", description = "手机短信验证码信息")
public class Sms {
	@ApiModelProperty("电话号码")
	private String phone; // 电话号码
	@ApiModelProperty("短信验证码")
	private String code; // 短信验证码
	@ApiModelProperty(" 短信验证码生成时间")
	private Long time; // 短信验证码生成时间

	public Sms() {
	}

	public Sms(String phone, String code, Long time) {
		this.phone = phone;
		this.code = code;
		this.time = time;
	}
}
