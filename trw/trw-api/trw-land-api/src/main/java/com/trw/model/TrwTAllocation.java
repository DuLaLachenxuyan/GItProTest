
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
 * @author jianglingyun123
 * @since 2018-06-20
 */
@Getter
@Setter
@TableName("trw_t_allocation")
@ApiModel(value = "经纪人土地关系表")
public class TrwTAllocation extends Model<TrwTAllocation> {

	private static final long serialVersionUID = 6144767606666671744L;
	/**
     * 主键
     */
	 @TableId(value = "id", type = IdType.INPUT)
     @ApiModelProperty(value = "经纪人土地关联id")
    private String id;
    /**
     * 土地id
     */
    @ApiModelProperty(value = "土地id")
    private String productid;
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
