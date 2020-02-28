package com.trw.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 16:00 2018/7/20
 * @Modified By:
 */
@Getter
@Setter
@ApiModel(value = "服务商下推荐土地表")
public class FaciRecommend {
    /**
     * 推荐id
     */
    @ApiModelProperty(value = "推荐id")
    private String remdlandID;
    /**
     * 推荐详情
     */
    @ApiModelProperty(value = "推荐详情")
    private String remdlandDetails;
    /**
     * 推荐开始时间
     */
    @ApiModelProperty(value = "推荐开始时间")
    private Date recommendTime;
    /**
     * 推荐结束时间
     */
    @ApiModelProperty(value = "推荐结束时间")
    private Date expirationTime;
    /**
     * 土地id
     */
    @ApiModelProperty(value = "土地id")
    private String productid;
    /**
     * 推荐地区
     */
    @ApiModelProperty(value = "推荐地区")
    private String regional;
    /**
     * 推荐位置
     */
    @ApiModelProperty(value = "推荐位置")
    private String position;
    /**
     * 土地状态
     */
    @ApiModelProperty(value = "土地状态")
    private String landState;
    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String purpose;
    
    /**
     * 土地图片
     */
    @ApiModelProperty(value = "土地图片")
    private String coverimg;
    
    /**
     * 推荐状态
     */
    @ApiModelProperty(value = "推荐状态")
    private String state;
    /**
     * 推荐位置
     */
    @ApiModelProperty(value = "推荐位置")
    private String recommendArea;
    
}
