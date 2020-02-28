package com.trw.model;

import java.io.Serializable;
import java.util.Date;

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
 * 管理员表
 * </p>
 *
 */
@Getter
@Setter
@TableName("TRW_T_USER")
@ApiModel(value = "用户表")
public class TrwTUser extends Model<TrwTUser> {
	private static final long serialVersionUID = -3557999514525030635L;
	/**
     * 主键id
     */
	@TableId(type=IdType.INPUT)
	private String userid;
    /**
     * 头像
     */
	@ApiModelProperty(value = "头像")
	private String avatar;
    /**
     * 账号
     */
	@ApiModelProperty(value = "账号")
	private String account;
    /**
     * 密码
     */
	@ApiModelProperty(value = "密码")
	private String pwd;
    /**
     * md5密码盐
     */
	@ApiModelProperty(value = "md5密码盐")
	private String salt;
    /**
     * 名字
     */
	@ApiModelProperty(value = "名字")
	private String name;
    /**
     * 性别（1：男 2：女）
     */
	@ApiModelProperty(value="性别（1：男 2：女）",example="1")
	private Integer sex;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value="电子邮件")
	private String email;
    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
	private String phone;
    /**
     * 最近使用角色
     */
    @ApiModelProperty(value="最近使用角色")
	private String currole;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
	@ApiModelProperty(value="状态(1：启用  2：冻结  3：删除）",example="01")
	private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
	private Date createtime;
	
	/**
	 * 学历
	 */
	@ApiModelProperty(value="学历")
	private String eduback;
	/**
	 * 专业
	 */
	@ApiModelProperty(value="联系地址")
	private String profession;
	/**
     * 联系地址
     */
	 @ApiModelProperty(value="联系地址")
    private String address;
    /**
     * 微信号
     */
    @ApiModelProperty(value="微信号")
    private String wxnumber;
	

	@Override
	protected Serializable pkVal() {
		return this.userid;
	}

	
}
