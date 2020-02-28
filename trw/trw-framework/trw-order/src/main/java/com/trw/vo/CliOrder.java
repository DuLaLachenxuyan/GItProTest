package com.trw.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: CliOrder
 * @Description: 用户端的订单，订单详情
 * @author haochen
 * @date 2018年7月9日 下午1:39:28
 *
 */
@Data
@ApiModel(value = "用户端订单", description = "用户端订单")
public class CliOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3728234755274206751L;
	/*
	 * o.orderid, o.ordertime, o.orderstat, o.appointment, o.remark, o.charges,
	 * o.fee, o.num, o.faciid, l.productid, l.imgs, l.transMode, l.productname,
	 * l.productdesc, l.location, f.faciname, l.acreage
	 */

	/**
	 * 经纪人电话
	 */
	@ApiModelProperty(value = "经纪人电话")
	private String aphone;

	/**
	 * 经纪人名字
	 */
	@ApiModelProperty(value = "经纪人名字")
	private String aname;
	/**
	 * 评价内容
	 */
	@ApiModelProperty(value = "评价内容")
	private String bevaltent;

	/**
	 * 经纪人评价
	 */
	@ApiModelProperty(value = "经纪人评价")
	private String angeopinion;
	/**
	 * 评价时间
	 */
	@ApiModelProperty(value = "评价时间")
	private Date createtime;
	/**
	 * 流水号
	 */
	@ApiModelProperty(value = "流水号")
	private String id;
	/**
	 * 01土地订单,02带看订单
	 */
	@ApiModelProperty(value = "订单状态,01-土地订单,02-带看订单")
	private String ordertype;
	/**
	 * 土地价格
	 */
	@ApiModelProperty(value = "土地价格")
	private BigDecimal price;
	/**
	 * 服务商名字
	 */
	@ApiModelProperty(value = "服务商名字")
	private String faciname;
	/**
	 * 土地面积
	 */
	@ApiModelProperty(value = "土地面积")
	private Integer acreage;
	/**
	 * 所在区域
	 */
	@ApiModelProperty(value = "所在区域")
	private String location;
	/**
	 * 土地介绍
	 */
	@ApiModelProperty(value = "土地介绍")
	private String productDesc;
	/**
	 * 土地标题
	 */
	@ApiModelProperty(value = "土地标题")
	private String productname;
	/**
	 * 流转方式
	 */
	@ApiModelProperty(value = "流转方式")
	private String transMode;

	/**
	 * 图片
	 */
	@ApiModelProperty(value = "图片")
	private String imgs;

	/**
	 * 第一张图片
	 */
	@ApiModelProperty(value = "第一张图片")
	private String firstImg;
	/**
	 * 土地编码id
	 */
	@ApiModelProperty(value = "土地编码id")
	private String productid;
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String orderid;
	/**
	 * 订单时间
	 */
	@ApiModelProperty(value = "订单时间")
	private Date ordertime;
	/**
	 * 预约时间
	 */
	@ApiModelProperty(value = "预约时间")
	private Date appointment;
	/**
	 * 订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单,06-带看中,07-待确认)
	 */
	@ApiModelProperty(value = "订单状态(01-待支付,02-已支付,03-确认完成,04-评价完成,05-取消订单,06-带看中,07-待确认)")
	private String orderstat;
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
	 * 带看次数
	 */
	@ApiModelProperty(value = "带看次数")
	private Integer num;
	/**
	 * 服务商id
	 */
	@ApiModelProperty(value = "服务商id")
	private String faciid;
	/**
	 * 经纪人id
	 */
	@ApiModelProperty(value = "经纪人id")
	private String agentid;

	public CliOrder() {
	}

}
