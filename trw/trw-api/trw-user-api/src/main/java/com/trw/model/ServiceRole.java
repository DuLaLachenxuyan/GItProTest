package com.trw.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter; 

/**
* @ClassName: ServiceRole 
* @Description: 服务商角表
* @author luojing
* @date 2018年7月12日 上午11:42:25 
*
 */
@Getter
@Setter
@TableName("trw_t_service_role")
@ApiModel(value="服务商角表实体")
public class ServiceRole extends Model<ServiceRole> {

	private static final long serialVersionUID = -1892704044108560862L;
	/**
     * 服务商角色ID
     */
	@ApiModelProperty(value="主键id")
    private String roleId;
    /**
     * 服务商角色名称
     */
	@ApiModelProperty(value="服务商角色名称")
    private String roleName;
    /**
     * 创建时间
     */
	@ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
	@ApiModelProperty(value="创建人")
    private String createUser;
    /**
     * 备注
     */
	@ApiModelProperty(value="备注")
    private String remark;
    
    /**
     * 经纪中心ID
     */
	@ApiModelProperty(value="经纪中心ID")
    private String faciid;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
    

   
}
