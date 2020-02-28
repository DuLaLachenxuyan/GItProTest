package com.trw.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: SerOrder
 * @Description: 服务中心的订单，详情
 * @author haochen
 * @date 2018年7月9日 下午2:26:53
 *
 */
@Data
@ApiModel(value = "服务中心订单", description = "服务中心订单")
public class SerOrder implements Serializable {

	/*
	 * o.orderid, o.ordertime, o.appointment, o.takemodel, o.cliname, o.clitel,
	 * l.transMode, l.productname, l.landType, l.acreage, u.name, u.phone,
	 * a.aname, a.aphone
	 */
	/*
	 * o.orderid, o.ordertime, o.appointment, o.takemodel, o.orderstat,
	 * o.ordertype, l.address, l.location, l.purpose, l.productid, l.acreage,
	 * l.rentyear, l.transMode, l.productname, l.landType, l.landContact,
	 * l.phone, a.aname, a.aphone
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 9140298745654086748L;
	
	/**
	 * 土地发布时间
	 * 
	 */
	@ApiModelProperty(value = "土地发布时间")
	 private LocalDateTime  reporttime;
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
	 * 土地联系人电话
	 */
	@ApiModelProperty(value = "土地联系人电话")
	private String lphone;

	/**
	 * 土地联系人
	 */
	@ApiModelProperty(value = "土地联系人")
	private String landContact;
	/**
	 * 流转年限
	 */
	@ApiModelProperty(value = "流转年限")
	private Integer rentyear;
	/**
	 * 土地编码
	 */
	@ApiModelProperty(value = "土地编码")
	private String productid;
	/**
	 * 土地用途
	 */
	@ApiModelProperty(value = "土地用途")
	private String purpose;
	/**
	 * 所在区域
	 */
	@ApiModelProperty(value = "所在区域")
	private String location;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String address;

	/**
	 * 订单类型
	 */
	@ApiModelProperty(value = "订单类型")
	private String ordertype;
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
	 * 用户电话
	 */
	@ApiModelProperty(value = "用户电话")
	private String phone;
	/**
	 * 用户名字
	 */
	@ApiModelProperty(value = "用户名字")
	private String name;

	/**
	 * 土地面积
	 */
	@ApiModelProperty(value = "土地面积")
	private Integer acreage;
	/**
	 * 土地类型
	 */
	@ApiModelProperty(value = "土地类型")
	private String landType;

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
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String orderid;
	/**
	 * 订单时间
	 */
	@ApiModelProperty(value = "订单时间")
	private LocalDateTime ordertime;
	/**
	 * 预约时间
	 */
	@ApiModelProperty(value = "预约时间")
	private LocalDateTime appointment;
	/**
	 * 订单状态
	 */
	@ApiModelProperty(value = "订单状态")
	private String orderstat;
	/**
	 * 需求备注
	 */
	@ApiModelProperty(value = "需求备注")
	private String remark;
	/**
	 * 看地模式
	 */
	@ApiModelProperty(value = "看地模式")
	private String takemodel;
	/**
	 * 客户名字
	 */
	@ApiModelProperty(value = "客户名字")
	private String cliname;
	/**
	 * 客户电话
	 */
	@ApiModelProperty(value = "客户电话")
	private String clitel;
	/**
	 * 评价时间
	 */
	@ApiModelProperty(value = "评价时间")
	private LocalDateTime createtime;
	/**
	 * 评价经纪人
	 */
	@ApiModelProperty(value = "评价经纪人")
	private String angeopinion;
	/**
	 * 评价内容
	 */
	@ApiModelProperty(value = "评价内容")
	private String bevaltent;
	/**
	 * 经纪人头像
	 */
	@ApiModelProperty(value = "经纪人头像")
	private String portrait;
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
}
