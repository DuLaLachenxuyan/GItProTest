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
 *
 * @author jianglingyun123
 * @since 2018-06-22
 */
@Getter
@Setter
@ApiModel("消息表")
@TableName("TRW_T_MSG")
public class TrwTMsg extends Model<TrwTMsg> {
	private static final long serialVersionUID = 3812922886217230366L;
	/**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty("消息主键id")
    private Integer id;
    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String msgtext;
    /**
     * 消息提醒人
     */
    @ApiModelProperty("消息提醒人")
    private String userid;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createtime;

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

}
