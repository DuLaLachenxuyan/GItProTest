package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jianglingyun123
 * @since 2018-05-26
 */

@Getter
@Setter
@TableName("TRW_T_ORDER")
@ApiModel(value = "订单", description = "订单pojo")
public class TrwTOrder extends Model<TrwTOrder> {

	private static final long serialVersionUID = 237837905251393942L;
	/**
	 * 订单id
	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "订单id")
	private String orderid;
	/**
	 * 订单时间
	 */

	@ApiModelProperty(value = "下单时间")
	private Date ordertime;
	/**
	 * 预约时间
	 */
	@ApiModelProperty(value = "预约时间")
	@Future(message = "下单时间在当前时间之后")
	private Date appointment;
	/**
	 * 需求备注
	 */
	@ApiModelProperty(value = "需求备注")
	private String remark;
	/**
	 * 佣金
	 */
	@ApiModelProperty(value = "佣金")
	private BigDecimal charges;
	/**
	 * 带看费
	 */
	@ApiModelProperty(value = "带看费")
	private BigDecimal fee;
	/**
	 * 服务商id
	 */
	@ApiModelProperty(value = "服务商id")
	private String faciid;
	/**
	 * 土地编码
	 */
	@ApiModelProperty(value = "土地编码id")
	@NotBlank(message = "土地编码id不能为空")
	private String productid;
	/**
	 * 用户id
	 */
	@TableField("user_id")
	@ApiModelProperty(value = "用户id")
	@NotBlank(message = "用户id不能为空")
	private String userId;
	/**
	 * 看地模式
	 */
	@ApiModelProperty(value = "看地模式")
	private String takemodel;
	/**
	 * 订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单,06-带看中,07-待确认)
	 */
	@ApiModelProperty(value = "订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单,06-带看中,07-待确认)")
	@NotBlank(message = "订单状态不能为空")
	private String orderstat;
	/**
	 * 01土地订单,02带看订单
	 */
	@ApiModelProperty(value = "01-土地订单,02-带看订单")
	private String ordertype;
	/**
	 * 带看次数
	 */
	@ApiModelProperty(value = "带看次数")
	private Integer num;
	/**
	 * 经纪人id
	 */
	@ApiModelProperty(value = "经纪人id")
	private String agentId;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "操作时间")
	private LocalDateTime operatorTime;
	/**
	 * 操作人
	 */
	@ApiModelProperty(value = "操作人")
	private String operator;

	@Override
	protected Serializable pkVal() {
		return this.orderid;
	}
	
}
