package com.trw.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "NoticeMsg", description = "消息")
@Data
public class NoticeMsg implements Serializable {
	 
	private static final long serialVersionUID = 6961440022292080146L;
	@ApiModelProperty(value = "消息模板ID")
	@NotBlank(message="消息模板ID不能为空")
	private String msgId;
	@ApiModelProperty(value = "手机号")
	@NotBlank(message="手机号不能为空")
	private String phone;
	@ApiModelProperty(value = "消息参数")
	@NotBlank(message="消息参数不能为空")
	private String param;
	@ApiModelProperty(value = "收到短信的用户")
	private String userId;
	@ApiModelProperty(value = "是否需要发出短信")
	private String needSMS;

}
