package com.trw.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * @author
 * @since 2018-08-07
 */
@Getter
@Setter
@ApiModel(value = "积分明细表")
@TableName("trw_t_service_score_detail")
public class ServiceScoreDetail extends Model<ServiceScoreDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private LocalDateTime date;
    /**
     * 类型(01增加02减少)
     */
    @ApiModelProperty(value = "类型(01增加02减少)")
    private String type;
    /**
     * 变动积分
     */
    @ApiModelProperty(value = "变动积分")
    private Integer score;
    /**
     * 服务商id
     */
    @ApiModelProperty(value = "服务商id")
    private String faciid;
    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    private String ruleid;
    /**
     * 土地编号
     */
    @ApiModelProperty(value = "土地编号")
    private String productid;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}
