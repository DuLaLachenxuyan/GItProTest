package com.trw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ApiModel(value = "资金账户信息")
public class AccountVo implements Serializable {

	private static final long serialVersionUID = 1340261415807676213L;
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userid;
	/**
	 * 账户余额
	 */
	@ApiModelProperty("账户余额")
	private BigDecimal balance;
	/**
	 * 剩余带看次数
	 */
	@ApiModelProperty("剩余带看次数")
	private Integer retimes;
	/**
	 * 保证金
	 */
	@ApiModelProperty("保证金")
	private BigDecimal deposit;
	/**
	 * 流水号
	 */
	@ApiModelProperty("流水号")
	private String id;
	/**
	 * 创建日期
	 */
	@ApiModelProperty("创建日期")
	private String createTime;
	/**
	 * 金额
	 */
	@ApiModelProperty("金额")
	private BigDecimal totalAmount;
	/**
	 * 资金流向(01-收入，02-支出)
	 */
	@ApiModelProperty("资金流向(01-收入，02-支出)")
	private String fundFlow;
	/**
	 * 资金类型(01-普通资金,02-保证金)
	 */
	@ApiModelProperty("资金类型(01-普通资金,02-保证金)")
	private String fundType;
	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String body;
	/**
	 * 交易号
	 */
	@ApiModelProperty("交易号")
	private String tradeNo;
	
}
