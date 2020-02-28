package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
 * @author gongzhen123
 * @since 2018-06-14
 */
@Getter
@Setter
@TableName("trw_t_client")
@ApiModel(value="客户实体")
public class LandClient extends Model<LandClient> {

	private static final long serialVersionUID = 3889330223239910769L;

	@TableId(value = "needid", type = IdType.INPUT)
	@ApiModelProperty(value="需求id")
    private String needid;
	
	@ApiModelProperty(value="所在区域")
    private String location;
	
	@ApiModelProperty(value="流转方式")
    private String transMode;
   
	@ApiModelProperty(value="土地类型")
    private String landType;
  
	@ApiModelProperty(value="最小土地面积")
    private Float minAcreage;
    
	@ApiModelProperty(value="最大土地面积")
    private Float maxAcreage;
   
	@ApiModelProperty(value="最小交易费用")
    private BigDecimal minPrice;
    
	@ApiModelProperty(value="最大交易费用")
    private BigDecimal maxPrice;
    
	@ApiModelProperty(value="流转年限")
    private BigDecimal rentyear;
   
	@ApiModelProperty(value="信息标题")
    private String mtitle;
   
	@ApiModelProperty(value="备注")
    private String remark;
   
	@ApiModelProperty(value="联系人姓名")
    private String landContact;
   
	@ApiModelProperty(value="联系电话")
    private String phone;
   
	@ApiModelProperty(value="客户来源")
    private String psource;
  
	@ApiModelProperty(value="意向等级")
    private String ingrade;
    
	@ApiModelProperty(value="发布人")
    private String reporter;
   
	@ApiModelProperty(value="发布时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime reporttime;
   
	@ApiModelProperty(value="审批人")
    private String reviewer;
    
	@ApiModelProperty(value="审批时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime reviewtime;
    
	@ApiModelProperty(value="更新时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updatetime;
   
	@ApiModelProperty(value="土地所有权")
    private String ownership;
    
	@ApiModelProperty(value="土地标签")
    private String landLabel;
   
	@ApiModelProperty(value="服务商ID")
    private String faciid;
 
	@ApiModelProperty(value="土地价格")
    private BigDecimal price;

	@ApiModelProperty(value="交易状态")
    private String landState;
  
	@ApiModelProperty(value="经纪人id")
    private String agentid;
  
	@ApiModelProperty(value="是否为公司客户")
    private Integer isCustomer;
  
	@ApiModelProperty(value="土地用途")
    private String purpose;
	
    @ApiModelProperty(value="电话核实(01为已核实，02为未核实)")
	private String phoneCheck;
    
    @ApiModelProperty(value="是否删除(01未删除，02已删除)")
 	private String isDel;
    
    @ApiModelProperty(value = "操作人")
    private String operator;
    
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operatorTime;

	@Override
    protected Serializable pkVal() {
        return this.needid;
    }

}
