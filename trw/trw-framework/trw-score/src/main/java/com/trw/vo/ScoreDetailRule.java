package com.trw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 11:11 2018/8/10
 * @Modified By:
 */
@Getter
@Setter
@ApiModel(value = "积分明细和规则")
public class ScoreDetailRule implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "流水号")
    private String id;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "类型(01增加02减少)")
    private String type;
    @ApiModelProperty(value = "变动积分")
    private Integer score;
    @ApiModelProperty(value = "服务商id")
    private String faciid;
    @ApiModelProperty(value = "规则id")
    private String ruleid;
    @ApiModelProperty(value = "积分描述")
    private String description;
    @ApiModelProperty(value = "土地编号")
    private String productid;
    
}
