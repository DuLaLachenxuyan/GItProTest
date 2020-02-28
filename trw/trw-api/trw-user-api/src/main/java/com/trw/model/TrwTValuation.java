package com.trw.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("trw_t_valuation")
@ApiModel(value = "评价表")
public class TrwTValuation extends Model<TrwTValuation> {

	private static final long serialVersionUID = 6109075195016169193L;
	@ApiModelProperty("评价id")
	private Integer id;
	/**
	 * 经纪人最终评价() 保留字段
	 */
	@ApiModelProperty("经纪人最终评价")
	private String endangeopin;
	/**
	 * 服务中心id
	 */
	@ApiModelProperty("服务中心id")
	private String faciid;

	/**
	 * 订单id
	 */
	@ApiModelProperty("订单id")
	@NotBlank(message = "订单id不能为空")
	private String orderid;
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userid;
	/**
	 * 经纪人id
	 */
	@ApiModelProperty("经纪人id")
	@NotBlank(message = "经纪人id不能为空")
	private String angentid;
	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private Date createtime;

	/**
	 * 土地id
	 */
	@ApiModelProperty("土地id")
	@NotBlank(message = "土地id不能为空")
	private String productid;

	/**
	 * 对经纪人评价
	 */
	@ApiModelProperty("对经纪人评价")
	private String angeopinion;
	/**
	 * 评价内容
	 */
	@ApiModelProperty("评价内容")
	@Length(min =0,max=60,message="评价内容不超过60字")
	private String bevaltent;

	@Override
	protected Serializable pkVal() {
		return id;
	}
}
