package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @ClassName: ServiceAgentRole 
* @Description: 服务商用户角色表
* @author luojing
* @date 2018年7月12日 上午11:41:44 
*
 */
@Getter
@Setter
@TableName("trw_t_service_agent_role")
@ApiModel(value="经纪人权限实体")
public class ServiceAgentRole extends Model<ServiceAgentRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="主键id")
    @TableId(value = "arId", type = IdType.INPUT)
    private String arId;
    /**
     * 经纪人
     */
    @ApiModelProperty(value="经纪人id")
    private String agentId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value="角色id")
    private String roleId;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    
    @Override
    protected Serializable pkVal() {
        return this.arId;
    }
    
}
