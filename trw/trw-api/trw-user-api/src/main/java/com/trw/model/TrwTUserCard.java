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
* @ClassName: TrwTUserCard 
* @Description: 用户身份证信息
* @author luojing
* @date 2018年7月17日 下午1:32:14 
*
 */
@TableName("trw_t_user_card")
@ApiModel(value = "用户身份证信息")
@Getter
@Setter
public class TrwTUserCard extends Model<TrwTUserCard> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(type=IdType.INPUT)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID")
    private String userid;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String carNumber;
    /**
     * 身份证正面存放路径
     */
    @ApiModelProperty(value = "身份证正面存放路径")
    private String positive;
    
    /**
     * 身份证反面存放路径
     */
    @ApiModelProperty(value = "身份证反面存放路径")
    private String negative;
    
    /**
     * 审核状态：0审核通过  1审核中  -1审核失败
     */
    @ApiModelProperty(value = "审核状态：0审核通过  1审核中  -1审核失败")
    private Integer status;
    /**
     * 失败消息
     */
    @ApiModelProperty(value = "失败消息")
    private String failmsg;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String reviewuser;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private Date reviewtime;


    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	 
}
