package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author haochen123
 * @since 2018-06-30
 */
@Getter
@Setter
@TableName("trw_t_deal")
@ApiModel(value = "交易", description = "交易pojo")
public class TrwTDeal extends Model<TrwTDeal> {
	private static final long serialVersionUID = 1627047855293463387L;
	/**
	 * 交易主键id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "交易主键id")
	private int id;

	/**
	 * 交易单号
	 */
	@ApiModelProperty(value = "交易单号")
	private String dealid;
	/**
	 * 交易时间
	 */
	private LocalDate dealtime;
	/**
	 * 卖家电话
	 */
	@ApiModelProperty(value = "卖家电话")
	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "卖家手机号码格式错误")
	@NotBlank(message = "卖家电话不能为空")
	private String selltel;
	/**
	 * 卖家名字
	 */
	@ApiModelProperty(value = "卖家名字")
	@NotBlank(message = "卖家名字不能为空")
	private String seller;
	/**
	 * 卖家身份证
	 */
	@ApiModelProperty(value = "卖家身份证")
	@Length(min = 18, max = 18, message = "卖家身份证须在18位")
	private String sellID;
	/**
	 * 卖家地址
	 */
	@ApiModelProperty(value = "卖家地址")
	private String selladdress;
	/**
	 * 卖家开户银行
	 */
	@ApiModelProperty(value = "卖家开户银行")
	private String sellbank;
	/**
	 * 卖家开户银行卡号
	 */
	@ApiModelProperty(value = "卖家开户银行卡号")
	private String sellbanknum;
	/**
	 * 卖家支行名称
	 */
	@ApiModelProperty(value = "卖家支行名称")
	private String sellbankbranch;
	/**
	 * 买家名字
	 */
	@ApiModelProperty(value = "买家名字")
	@NotBlank(message = "买家名称不能为空")
	private String buyer;
	/**
	 * 买家电话
	 */
	@ApiModelProperty(value = "买家电话")
	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "买家手机号码格式错误")
	@NotBlank(message = "买家手机号码不能为空")
	private String buytel;
	/**
	 * 买家身份证
	 */
	@ApiModelProperty(value = "买家身份证")
	@Length(min = 18, max = 18, message = "买家身份证须在18位")
	private String buyID;
	/**
	 * 买家地址
	 */
	@ApiModelProperty(value = "买家地址")
	private String buyaddress;
	/**
	 * 买家开户银行
	 */
	@ApiModelProperty(value = "买家开户银行")
	private String buybank;
	/**
	 * 买家开户银行卡号
	 */
	@ApiModelProperty(value = "买家开户银行卡号")
	private String buybanknum;
	/**
	 * 买家支行名称
	 */
	@ApiModelProperty(value = "买家支行名称")
	private String buybankbranch;

	/**
	 * 土地id
	 */
	@ApiModelProperty(value = "土地id")
	@NotBlank(message = " 土地id不能为空")
	private String productid;
	/**
	 * 需求id
	 */
	@ApiModelProperty(value = "需求id")
	private String needid;

	/**
	 * 土地标题
	 */
	@ApiModelProperty(value = "土地标题")
	@NotBlank(message = " 土地标题名称不能为空")
	private String productname;
	/**
	 * 所在地区
	 */
	@ApiModelProperty(value = "所在地区")
	private String location;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String address;
	/**
	 * 卖家附件文件
	 */
	@ApiModelProperty(value = "卖家附件文件")
	@NotBlank(message = " 卖家附件文件不能为空")
	private String sellfile;
	/**
	 * 买家附件文件
	 */
	@ApiModelProperty(value = "买家附件文件")
	@NotBlank(message = " 买家附件文件不能为空")
	private String buyfile;
	/**
	 * 创建时间
	 */
	private LocalDateTime createtime;
	/**
	 * 经纪人名字
	 */
	@ApiModelProperty(value = "经纪人名字")
	private String aname;
	/**
	 * 交易状态(01已签约,02交易已备案,03买家已确认,04交易完成,05交易终止)
	 */
	@ApiModelProperty(value = "交易状态(01已签约,02交易已备案,03买家已确认,04交易完成,05交易终止)")
	private String dealstat;
	/**
	 * 经纪人id
	 */
	@ApiModelProperty(value = "经纪人id")
	private String agentid;
	/**
	 * 服务商id
	 */
	@ApiModelProperty(value = "服务商id")
	private String faciid;
	/**
	 * 交易价格
	 */
	@ApiModelProperty(value = "交易价格")
	private BigDecimal dealprice;
	/**
	 * 交易备注
	 */
	@ApiModelProperty(value = "交易备注")
	private String remark;
	
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
		return this.id;
	}
	

}
