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
 * @author linhai123
 * @since 2018-07-18
 */
@Getter
@Setter
@TableName("pfa_f_farmers")
@ApiModel(value = "大国农匠表")
public class PfaFFarmers extends Model<PfaFFarmers> {

    private static final long serialVersionUID = 1L;

    /**
     * 大国农匠id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 农匠姓名
     */
    @ApiModelProperty(value = "农匠姓名")
    private String name;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idcard;
    /**
     * 所在单位名称
     */
    @ApiModelProperty(value = "所在单位名称")
    private String companyName;
    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
    private String position;
    /**
     * 学历
     */
    @ApiModelProperty(value = "学历")
    private String eduback;
    /**
     * 工作地址
     */
    @ApiModelProperty(value = "工作地址")
    private String workingAddress;
    /**
     * 户口所在地
     */
    @ApiModelProperty(value = "户口所在地")
    private String registeredResidence;
    /**
     * 工龄
     */
    @ApiModelProperty(value = "工龄")
    private String lengthService;
    /**
     * 规模（亩、头、只）
     */
    @ApiModelProperty(value = "规模（亩、头、只）")
    private String scale;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 自我介绍
     */
    @ApiModelProperty(value = "自我介绍")
    private String introduction;
    /**
     * 所在区域
     */
    @ApiModelProperty(value = "所在区域")
    private String location;
    /**
     * 农匠类型
     */
    @ApiModelProperty(value = "农匠类型")
    private String farmerType;
    /**
     * 农匠流转方式
     */
    @ApiModelProperty(value = "农匠流转方式")
    private String farmerTransMode;
    /**
     * 农匠流转用途
     */
    @ApiModelProperty(value = "农匠流转用途")
    private String transModeUse;
    /**
     * 认证
     */
    @ApiModelProperty(value = "认证")
    private String certification;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

   
}
