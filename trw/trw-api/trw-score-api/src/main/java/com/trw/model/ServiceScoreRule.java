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
@Getter
@Setter
@ApiModel(value = "积分规则表")
@TableName("trw_t_service_score_rule")
public class ServiceScoreRule extends Model<ServiceScoreRule> {

    private static final long serialVersionUID = 1L;

    /**
     * 规则id
     */
    @ApiModelProperty(value = "规则id")
    @TableId(value = "ruleid", type = IdType.AUTO)
    private Integer ruleid;
    /**
     * 规则类型(01增,02减)
     */
    @ApiModelProperty(value = "规则类型(01增,02减)")
    private String ruletype;
    /**
     * 分数
     */
    @ApiModelProperty(value = "分数")
    private Integer rulescore;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    
    @Override
    protected Serializable pkVal() {
        return this.ruleid;
    }
    
}
