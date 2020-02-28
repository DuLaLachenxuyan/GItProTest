package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 通知表
 * </p>
 *
 * @author haochen123
 * @since 2018-07-20
 */
@Getter
@Setter
@TableName("plat_t_notice")
@ApiModel(value = "通知表")
public class PlatTNotice extends Model<PlatTNotice> {

	private static final long serialVersionUID = -652214989218042200L;
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	private String title;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容")
	private String content;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createtime;
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String creater;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
