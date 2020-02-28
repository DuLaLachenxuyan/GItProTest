package com.trw.vo;

import java.io.Serializable;

import com.trw.model.TrwTDeal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "交易详情", description = "交易详情")
public class DealDetail extends TrwTDeal implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -6424077538464215845L;
	@ApiModelProperty(value = "流转年限")
	private String location;
	@ApiModelProperty(value = "土地用途")
	private String purpose;
	@ApiModelProperty(value = "流转年限")
	private Integer rentyear;
	public DealDetail() {
	}
}
