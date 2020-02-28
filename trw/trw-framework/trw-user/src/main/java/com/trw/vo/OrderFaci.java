package com.trw.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description:
 * 服务商评价
 * @Author: LinHai
 * @Date: Created in 17:04 2018/6/14
 * @Modified By:
 */
@ApiModel(value = "服务商详情")
@Getter
@Setter
public class OrderFaci {
    
    /**
     * 土地所在区域
     */
    @ApiModelProperty(value = "土地所在区域")
    private String location;
    
    /**
     * 土地面积
     */
    @ApiModelProperty(value = "土地面积")
    private Float acreage;
    
    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String purpose;
    
    /**
     * 流转方式
     */
    @ApiModelProperty(value = "流转方式")
    private String transMode;
    /**
     * 经纪人姓名
     */
    @ApiModelProperty(value = "经纪人姓名")
    private String aname;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;
    
    /**
     * 用户对经纪人评价
     */
    @ApiModelProperty(value = "用户对经纪人评价")
    private String angeopinion;
    
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgs;
    
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;
    
    /**
     * 经纪人评价平均分
     */
    @ApiModelProperty(value = "经纪人评价平均分")
   private String endangeopin;
   
}
