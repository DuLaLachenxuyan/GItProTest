package com.trw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * <p>
 * 收藏信息实体
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
@ApiModel(value = "收藏信息")
@Getter
@Setter
public class Enshrine {
	@ApiModelProperty(value = "收藏id")
	private Integer id;
	@ApiModelProperty(value = "土地id")
	private String landid;
	@ApiModelProperty(value = "收藏类型")
	private String ctypes;
	@ApiModelProperty(value = "收藏时间")
	private Date ctime;
	@ApiModelProperty(value = "是否暂停")
	private String earthSourceNote;
	@ApiModelProperty(value = "用户电话")
	private String phone;
	@ApiModelProperty(value = "土地状态")
	private String landState;
	@ApiModelProperty(value = "服务商名称")
	private String faciname;
	@ApiModelProperty(value = "服务商联系电话")
	private String mobile;
	@ApiModelProperty(value = "土地封面")
	private String coverimg;
	@ApiModelProperty(value = "流转年限")
	private String rentyear;
	@ApiModelProperty(value = "土地价格")
	private String price;
	@ApiModelProperty(value = "土地用途")
	private String purpose;
	@ApiModelProperty(value = "土地类型")
	private String landType;
	@ApiModelProperty(value = "土地面积")
	private String acreage;
	@ApiModelProperty(value = "区域")
	private String location;

}
