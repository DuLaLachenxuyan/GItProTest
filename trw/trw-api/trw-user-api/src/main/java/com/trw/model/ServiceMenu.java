package com.trw.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @ClassName: ServiceMenu 
* @Description: 服务商菜单表
* @author luojing
* @date 2018年7月12日 上午11:42:33 
*
 */
@Getter
@Setter
@TableName("trw_t_service_menu")
@ApiModel(value="服务商菜单表实体")
public class ServiceMenu extends Model<	ServiceMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value="主键id")
    private String menuId;
    
    /**
     * 菜单父编号,一级菜单为0 对应menuId
     */
    @ApiModelProperty(value="主菜单父编号,一级菜单为0 对应menuId")
    private String parentId;
    
    /**
     * 菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String text;
    
    /**
     * 授权
     */
    @ApiModelProperty(value=" 授权")
    private String perms;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value=" 菜单图标")
    private String icon;
    /**
     * url地址
     */
    @ApiModelProperty(value=" url地址")
    private String url;
    /**
     * 排序号
     */
    @ApiModelProperty(value=" 排序号")
    private Integer orderNum;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @ApiModelProperty(value=" 类型   0：目录   1：菜单   2：按钮")
    private Integer type;
    /**
     * 菜单状态 :  1:启用   0:不启用
     */
    @ApiModelProperty(value="菜单状态 :  1:启用   0:不启用")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value=" 创建时间")
    private Date createTime;
    
    @ApiModelProperty(value="备注")
    private String remark;
    
    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }
    
}
