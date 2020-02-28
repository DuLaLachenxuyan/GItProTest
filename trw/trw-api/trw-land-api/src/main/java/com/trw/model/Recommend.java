package com.trw.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2018-06-28
 */
@Getter
@Setter
@TableName("trw_t_recommend")
@ApiModel(value = "推荐表")
public class Recommend extends Model<Recommend> {

	private static final long serialVersionUID = -7900578386083838362L;
	/**
     * 业务主键ID
     */
    @ApiModelProperty(value = "业务主键id")
    @TableId(value="remdlandID",type = IdType.INPUT)	
    private String remdlandID;
   
    @ApiModelProperty(value = "推荐详情")
    private String remdlandDetails;
    /**
     * 推荐时间
     */
    @ApiModelProperty(value = "推荐时间")
    private String recommendTime;
    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private Date expirationTime;
    /**
     * 推荐区域
     */
    @ApiModelProperty(value = "推荐区域")
    private String recommendArea;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;
    /**
     * 土地ID
     */
    @ApiModelProperty(value = "土地ID")
    private String productid;
  
    /**
     * 客户id
     * @return
     */
    @ApiModelProperty(value = "客户id")
    private String needIds;
    
    /**
     * 01为推荐土地，02为推荐需求。03给土地推荐客户
     */
    @ApiModelProperty(value = "01为推荐土地，02为推荐需求。03给土地推荐客户")
    private String types;
    
    /**
     * 土地状态
     */
    @ApiModelProperty(value = "土地状态")
    private String landState;
    
	/**
	 * 推荐位置
	 */
	@ApiModelProperty(value = "推荐位置")
	private String position;
	
	/**
	 * 推荐地区
	 */
	@ApiModelProperty(value = "推荐地区")
	private String regional;
	
	/**
	 * 推荐状态
	 */
	@ApiModelProperty(value = "推荐状态")
	private String state;
    
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operator;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operatorTime;
	
	@Override
	protected Serializable pkVal() {
		return remdlandID;
	}
	

 
}
