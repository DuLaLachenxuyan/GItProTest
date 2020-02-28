package com.trw.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.trw.constant.Constant;
import com.trw.model.ServiceRoleMenu;
import com.trw.service.ServiceRoleMenuService;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import com.trw.vo.RoleMenuVo;

import io.swagger.annotations.ApiOperation;

/**
* @ClassName: ServiceRoleMenuController 
* @Description: 服务商角色菜单表 前端控制器
* @author luojing
* @date 2018年7月11日 下午1:57:37 
*
 */
@RestController
public class ServiceRoleMenuController extends BaseController {
	
	@Autowired
	private ServiceRoleMenuService serviceRoleMenuService;
	
	/**
	* @Title: updateRoleMenu 
	* @Description: 角色修改菜单信息
	* @author luojing
	* @param @param roleMenu
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月11日
	 */
	@RequestMapping(value="/auth/updateRoleMenu", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST",value = "角色修改菜单信息", notes = "角色修改菜单信息")
	public ResultMsg<String> updateRoleMenu(@RequestBody @Valid RoleMenuVo roleMenuVo,BindingResult result ){
		ResultMsg<String> msg = new ResultMsg<String>();
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				msg.setCode(Constant.CODE_FAIL);
				msg.setMsg(error.getDefaultMessage());
				return msg;
			}
		}
		List<ServiceRoleMenu> entityList = new ArrayList<>();
		if(StrKit.isNotEmpty(roleMenuVo.getMenuIds())) {
			String[] arr =roleMenuVo.getMenuIds().split(",");
			for(int i=0;i<arr.length;i++) {
				ServiceRoleMenu menu = new ServiceRoleMenu();
				menu.setMenuId(arr[i]);
				menu.setRemark(roleMenuVo.getRemark());
				menu.setRoleId(roleMenuVo.getRoleId());
				menu.setRmId(ToolUtil.generateId());
				entityList.add(menu);
			}
		}
		
		if(ToolUtil.isEmpty(entityList)) {
			msg.setCode(Constant.CODE_FAIL);
			msg.setMsg("菜单为空");
			return msg;
		}
		
		Wrapper<ServiceRoleMenu> wrapper =new EntityWrapper<ServiceRoleMenu>().eq("roleId", roleMenuVo.getRoleId());
		serviceRoleMenuService.delete(wrapper);
		boolean fag = serviceRoleMenuService.insertBatch(entityList);
		if(fag) {
			msg.setCode(Constant.CODE_SUCC);
		}else {
			msg.setCode(Constant.CODE_FAIL);
			msg.setMsg("菜单权限添加失败");
		}
		return msg;
	}
	
}

