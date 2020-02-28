package com.trw.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "角色对应菜单")
public class RoleMenuVo implements Serializable {

	private static final long serialVersionUID = -5916981689187753376L;
	/**
	 * 角色id
	 */
	@ApiModelProperty("roleId")
	@NotBlank(message="角色不能为空")
	private String roleId;
	@ApiModelProperty("菜单")
	@NotBlank(message="菜单不能为空")
	private String menuIds;
	@ApiModelProperty("备注")
	@NotBlank(message="经纪人中心不能为空")
	private String remark;
	 
}
