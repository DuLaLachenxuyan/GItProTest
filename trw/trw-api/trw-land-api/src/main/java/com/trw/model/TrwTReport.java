package com.trw.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-25
 */
@Getter
@Setter
@TableName("trw_t_report")
@ApiModel(value = "举报表")
public class TrwTReport extends Model<TrwTReport> {

	private static final long serialVersionUID = -747377620852921448L;
	/**
     * 举报编码
     */
	@TableId
    @ApiModelProperty(value = "举报id")
    private String reportid;
    /**
     * 举报人
     */
    @ApiModelProperty(value = "举报人")
    private String username;
    /**
     * 举报内容
     */
    @ApiModelProperty(value = "举报内容")
    private String content;
    /**
     * 土地编码
     */
    @ApiModelProperty(value = "土地编码")
    private String productid;

    @Override
    protected Serializable pkVal() {
        return this.reportid;
    }
    
}
