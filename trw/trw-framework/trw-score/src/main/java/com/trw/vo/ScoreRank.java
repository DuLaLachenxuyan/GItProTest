package com.trw.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 15:55 2018/8/10
 * @Modified By:
 */
@Getter
@Setter
@ApiModel(value = "积分排行")
public class ScoreRank {
    @ApiModelProperty(value = "服务商id")
    private String faciid;
    @ApiModelProperty(value = "总积分")
    private Integer totalscore;
    @ApiModelProperty(value = "服务商名称")
    private String faciname;
}
