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
 * @author gongzhen123
 * @since 2018-06-30
 */
@Getter
@Setter
@TableName("trw_t_records")
@ApiModel(value = "跟进表")
public class Records extends Model<Records> {

	private static final long serialVersionUID = 9067890472380472476L;
	/**
     * 跟进记录id
     */
    @ApiModelProperty(value = "跟进记录id")
    @TableId(value = "followid", type = IdType.INPUT)
    private String followid;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String ctName;
    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    private String ctPhone;
    /**
     * 跟进负责人
     */
    @ApiModelProperty(value = "跟进负责人")
    private String followPerson;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String text;
    /**
     * 操作
     */
    @ApiModelProperty(value = "操作")
    private String operation;
    /**
     * 跟进时间
     */
    @ApiModelProperty(value = "跟进时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date followTime;
   
    /**
     * 土地id,客户ID,订单id 
     */
    @ApiModelProperty(value = "土地id,客户ID,订单id")
    private String id;
    
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
        return this.followid;
    }
    
    
}
