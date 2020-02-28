package com.trw.model;

import java.io.Serializable;

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
@TableName("trw_t_service_score")
@Getter
@Setter
@ApiModel(value = "积分汇总表")
public class ServiceScore extends Model<ServiceScore> {

    private static final long serialVersionUID = 1L;

    /**
     * 服务商id
     */
    @ApiModelProperty(value = "服务商id")
    @TableId(value = "faciid", type = IdType.INPUT)
    private String faciid;
    /**
     * 总积分
     */
    @ApiModelProperty(value = "总积分")
    private Integer totalscore;

    
    @Override
    protected Serializable pkVal() {
        return this.faciid;
    }
}
