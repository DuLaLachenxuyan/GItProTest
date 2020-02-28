package com.trw.model;

import java.io.Serializable;
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
 * @since 2018-06-29
 */
@Getter
@Setter
@TableName("trw_t_needagent")
@ApiModel(value="需求分配经纪人记录实体")
public class TrwTNeedagent extends Model<TrwTNeedagent> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 需求id
     */
    @ApiModelProperty(value = "需求id")
    private String needid;
    /**
     * 经纪人id
     */
    @ApiModelProperty(value = "经纪人id")
    private String agentId;
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
        return this.id;
    }

   
}
