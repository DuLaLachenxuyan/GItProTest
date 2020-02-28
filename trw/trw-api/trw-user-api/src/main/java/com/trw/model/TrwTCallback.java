package com.trw.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-25
 */
@Getter
@Setter
@ApiModel("预约回电表")
@TableName("trw_t_callback")
public class TrwTCallback extends Model<TrwTCallback> {

	private static final long serialVersionUID = -2951338636045132749L;
	/**
	 * 预约回电id
	 */
	@TableId(type = IdType.UUID)
	@ApiModelProperty("预约回电id")
	private String callid;
	/**
	 * 提交时间
	 */
	@ApiModelProperty("提交时间")
	private Date applytime;
	/**
	 * 预约人名字
	 */
	@ApiModelProperty("预约人名字")
	private String callname;
	/**
	 * 土地id
	 */
	@ApiModelProperty("土地id")
	private String productid;
	/**
	 * 回电状态（01未处理，02已回电）
	 */
	@ApiModelProperty("回电状态(01未处理，02已回电)")
	private String callstat;
	/**
	 * 服务商id
	 */
	@ApiModelProperty("服务商id")
	private String faciid;
	/**
	 * 经纪人id
	 */
	@ApiModelProperty("经纪人id")
	private String agentid;
	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String note;
	/**
	 * 预约人电话
	 */
	@ApiModelProperty("预约人电话")
	private String callbacktel;
	/**
	 * 操作人
	 */
	@ApiModelProperty("操作人")
	private String operator;
	/**
	 * 操作时间
	 */
	@ApiModelProperty("操作时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime operatorTime;
	

	@Override
	protected Serializable pkVal() {
		return this.callid;
	}

	@Override
	public String toString() {
		return "TrwTCallback{" + "callid=" + callid + ", applytime=" + applytime + ", callname=" + callname
				+ ", productid=" + productid + ", callstat=" + callstat + ", faciid=" + faciid + ", note=" + note
				+ ", callbacktel=" + callbacktel + "}";
	}
}
