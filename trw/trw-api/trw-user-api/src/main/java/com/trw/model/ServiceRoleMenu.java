package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @ClassName: TrwTServiceRoleMenu 
* @Description: 服务商角色菜单表
* @author luojing
* @date 2018年7月12日 上午11:42:42 
*
 */
@Getter
@Setter
@TableName("trw_t_service_role_menu")
@ApiModel(value="服务商角色菜单表实体")
public class ServiceRoleMenu extends Model<ServiceRoleMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="主键id")
    private String rmId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value="角色ID")
    private String roleId;
    /**
     * 菜单
     */
    @ApiModelProperty(value="菜单")
    private String menuId;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.rmId;
    }
}
