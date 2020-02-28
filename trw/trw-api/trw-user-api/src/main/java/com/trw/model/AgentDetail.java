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
 * 经纪人明细表
 * </p>
 *
 * @author gongzhen123
 * @since 2018-07-25
 */
@Getter
@Setter
@TableName("trw_t_agent_detail")
@ApiModel(value="经纪人详情实体")
public class AgentDetail extends Model<AgentDetail> {

	private static final long serialVersionUID = -5278284036476795666L;
	/**
     * 经纪人id
     */
    @TableId(type=IdType.INPUT)
    @ApiModelProperty(value="经纪人id")
    private String id;
    /**
     * 从业年限
     */
    @ApiModelProperty(value="从业年限")
    private Integer workage;
    /**
     * 等级
     */
    @ApiModelProperty(value="等级")
    private String level;
    /**
     * 综合评分
     */
    @ApiModelProperty(value="综合评分")
    private String grade;
    /**
     * 带看次数
     */
    @ApiModelProperty(value="带看次数")
    private int lookNum;
    /**
     * 补贴次数
     */
    @ApiModelProperty(value="补贴次数")
    private int subsidyNum;
    /**
     * 是否展示在前端(1是 2否)
     */
    @ApiModelProperty(value="是否展示在前端")
    private String isHost;
    /**
     * 交易次数
     */
    @ApiModelProperty(value="交易次数")
    private int dealNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
