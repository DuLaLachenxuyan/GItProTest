package com.trw.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ScoreMsg", description = "积分消息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreMsg implements Serializable {
	 
	private static final long serialVersionUID = 4417903564570517090L;
 
	@ApiModelProperty(value = "服务中心")
	@NotBlank(message="服务中心不能为空")
	private String faciId;
	
	@ApiModelProperty(value = "规则id")
	private String ruleId;
	
//	@ApiModelProperty(value = "收入/支出")
//	private String type;
//	
//	@ApiModelProperty(value = "分数")
//	private int score;
	
	
 

}
