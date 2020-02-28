package com.trw.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceRole;
import com.trw.model.TrwTAgent;
import com.trw.service.ServiceRoleService;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName: ServiceRoleController
 * @Description: 服务商角表 前端控制器
 * @author luojing
 * @date 2018年7月11日 下午1:57:24
 *
 */
@RestController
public class ServiceRoleController extends BaseController {

	@Autowired
	private ServiceRoleService serviceRoleService;
	/**
	 * @Title: selectPageServiceRole
	 * @Description: 根据经纪人分页查询角色信息
	 * @author luojing
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<Page<ServiceRole>> 返回类型
	 * @throws @date
	 *             2018年7月17日
	 */
	@RequestMapping(value = "/auth/selectPageServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "根据经纪人分页查询角色信息", notes = "根据经纪人分页查询角色信息")
	public ResultMsg<Page<ServiceRole>> selectPageServiceRole() {
		ResultMsg<Page<ServiceRole>> result = new ResultMsg<Page<ServiceRole>>();
		TrwTAgent agent =ShiroKit.getUser();
		 
		// 根据服务商ID查询角色
		ServiceRole role = new ServiceRole();
		role.setFaciid(agent.getFaciid());
		EntityWrapper<ServiceRole> wrapper = new EntityWrapper<ServiceRole>(role);
		// 获取分页信息
		Page<ServiceRole> page = new PageFactory<ServiceRole>().defaultPage();
		Page<ServiceRole> pg = serviceRoleService.selectPage(page, wrapper);
		if (pg != null) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(pg);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("未查询到角色信息");
		}
		return result;
	}

	/**
	 * @Title: getServiceRole
	 * @Description: 查询角色信息详情
	 * @author luojing
	 * @param @param
	 *            role
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<ServiceRole> 返回类型
	 * @throws @date
	 *             2018年7月17日
	 */
	@RequestMapping(value = "/auth/getServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "查询角色信息详情", notes = "通过角色ID查询")
	public ResultMsg<ServiceRole> getServiceRole(@RequestBody ServiceRole role) {
		ResultMsg<ServiceRole> result = new ResultMsg<ServiceRole>();
		if (role != null) {
			ServiceRole rl = serviceRoleService.selectById(role.getRoleId());
			if (rl != null) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(rl);
			} else {
				result.setMsg("未查询到角色信息");
				result.setCode(Constant.CODE_FAIL);
			}
		} else {
			result.setMsg("角色编号不能为空");
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}

	/**
	 * @Title: addServiceRole
	 * @Description: "新增服务商角色
	 * @author luojing
	 * @param @param
	 *            role
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月11日
	 */
	@RequestMapping(value = "/auth/addServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "新增服务商角色", notes = "输入角色信息")
	public ResultMsg<String> addServiceRole(@RequestBody ServiceRole role) {
		ResultMsg<String> result = new ResultMsg<String>();
		TrwTAgent agent =ShiroKit.getUser();
		role.setFaciid(agent.getFaciid());
		role.setRoleId(ToolUtil.generateId());
		role.setFaciid(agent.getFaciid());
		boolean fag = serviceRoleService.insert(role);
		if (fag) {
			result.setCode(Constant.CODE_SUCC);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("角色信息添加失败");
		}
		return result;
	}

	/**
	 * @Title: updateServiceRole
	 * @Description: 修改服务商角色
	 * @author luojing
	 * @param @param
	 *            role
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月17日
	 */
	@RequestMapping(value = "/auth/updateServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "修改服务商角色", notes = "输入服务商角色ID")
	public ResultMsg<String> updateServiceRole(@RequestBody ServiceRole role) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (role != null) {
			boolean fag = serviceRoleService.updateById(role);
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("角色信息修改失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("角色信息不能为空");
		}
		return result;
	}

	/**
	 * @Title: delServiceRole
	 * @Description: 删除服务商角色
	 * @author luojing
	 * @param @param
	 *            role
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月11日
	 */
	@RequestMapping(value = "/auth/delServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "删除服务商角色", notes = "输入服务商角色Id")
	public ResultMsg<String> delServiceRole(@RequestBody ServiceRole role) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (role != null) {
			boolean fag = serviceRoleService.delServiceRole(role.getRoleId());
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("删除角色信息失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("删除条件不能为空");
		}
		return result;
	}

	/**
	 * @Title: batchDelServiceRole
	 * @Description: 批量删除服务商角色
	 * @author luojing
	 * @param @param
	 *            ids
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月11日
	 */
	@RequestMapping(value = "/auth/batchDelServiceRole", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "批量删除服务商角色", notes = "输入服务商角色Id")
	public ResultMsg<String> batchDelServiceRole(@RequestBody List<String> ids) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (!ids.isEmpty()) {
			boolean fag = serviceRoleService.batchDelServiceRole(ids);
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("删除角色信息失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("删除条件不能为空");
		}
		return result;
	}

	/**
	 * 
	 * @Title: selectServiceRole
	 * @Description: 根据经纪人id集合查询角色
	 * @author gongzhen
	 * @param @param
	 *            req
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<List<ServiceRole>> 返回类型
	 * @throws @date
	 *             2018年7月23日
	 */
	@RequestMapping(value = "/auth/selectServiceRole", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "查询所有角色", notes = "查询所有角色")
	public ResultMsg<List<ServiceRole>> selectServiceRole() {
		ResultMsg<List<ServiceRole>> result = new ResultMsg<List<ServiceRole>>();
		TrwTAgent agent =ShiroKit.getUser();
		
		Wrapper<ServiceRole> wrapper = new EntityWrapper<ServiceRole>().eq("faciid", agent.getFaciid());
		List<ServiceRole> list = serviceRoleService.selectList(wrapper);
		//手动添加经纪人角色
		ServiceRole role = new ServiceRole();
		role.setRoleId("2");
		role.setRoleName("经纪人");
		role.setCreateTime(LocalDateTime.now());
		list.add(role);
		
		if (!list.isEmpty()) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(list);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("未查询到角色信息");
		}
		return result;
	}

}
