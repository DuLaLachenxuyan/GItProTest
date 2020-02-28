package com.trw.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
 *
 * @author gongzhen123
 * @since 2018-07-14
 */

@Getter
@Setter
@TableName("trw_t_agent")
@ApiModel(value="经纪人实体")
public class TrwTAgent extends Model<TrwTAgent> {

    private static final long serialVersionUID = -6591748466253052542L;
    
    @TableId(value = "id", type = IdType.INPUT)	
    @ApiModelProperty(value="经纪人id")
    private String id;
  
    @ApiModelProperty(value="姓名")
    private String aname;

    @ApiModelProperty(value="性别")
    private String sex;

    @ApiModelProperty(value="用户名")
    private String aacount;

    @ApiModelProperty(value="密码")
    private String apwd;

    @ApiModelProperty(value="所属中心")
    private String faciid;
    
    @ApiModelProperty(value="手机")
    private String aphone;

    @ApiModelProperty(value="状态 1-启用,2-冻结,3-删除")
    private String astat;

    @ApiModelProperty(value="区域")
    private String areaId;

    @ApiModelProperty(value="详细地址")
    private String address;

    @ApiModelProperty(value="土地Id")
    private String productid;
  
    @ApiModelProperty(value="经纪人头像")
    private String portrait;
    
    @ApiModelProperty(value="自我介绍")
    private String introduce;
    
    @ApiModelProperty(value="类别")
    private String professionals;
   
    @ApiModelProperty(value="经纪人介绍图片")
    private String aimg;
   
    @ApiModelProperty(value="身份证")
    private String idcard;
   
    @ApiModelProperty(value="添加时间")
    private Date createTime;
    
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
