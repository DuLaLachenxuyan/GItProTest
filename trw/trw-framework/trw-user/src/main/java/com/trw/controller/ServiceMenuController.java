package com.trw.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceMenu;
import com.trw.model.TrwTAgent;
import com.trw.service.ServiceMenuService;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import com.trw.vo.Tree;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: ServiceMenuController
 * @Description: 服务商菜单表 前端控制器
 * @author luojing
 * @date 2018年7月11日 下午1:57:05
 */
@RestController
public class ServiceMenuController extends BaseController {

	@Autowired
	private ServiceMenuService serviceMenuService;

	/**
	 * @Title: addServiceMenu
	 * @Description: 新增服务商菜单
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/addServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "新增服务商菜单", notes = "输入菜单信息")
	public ResultMsg<String> addServiceMenu(@RequestBody ServiceMenu menu) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (menu != null) {
			menu.setMenuId(ToolUtil.generateId());
			boolean fag = serviceMenuService.insert(menu);
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("菜单信息添加失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("菜单信息不能为空");
		}
		return result;
	}

	/**
	 * @Title: updateServiceMenu
	 * @Description: 修改服务商菜单
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/updateServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "修改服务商菜单", notes = "输入菜单ID信息")
	public ResultMsg<String> updateServiceMenu(@RequestBody ServiceMenu menu) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (menu != null) {
			boolean fag = serviceMenuService.updateById(menu);
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("菜单信息修改失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("菜单信息不能为空");
		}
		return result;
	}

	/**
	 * @Title: delServiceMenu
	 * @Description: 删除服务商菜单
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/delServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "删除服务商菜单", notes = "输入服务商菜单Id")
	public ResultMsg<String> delServiceMenu(@RequestBody ServiceMenu menu) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (menu != null) {
			boolean fag = serviceMenuService.delServiceMenu(menu.getMenuId());
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("删除服务商菜单失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("删除条件不能为空");
		}
		return result;
	}

	/**
	 * @Title: batchDelServiceMenu
	 * @Description: 批量删除服务商菜单
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/batchDelServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "批量删除服务商菜单", notes = "输入服务商菜单Id")
	public ResultMsg<String> batchDelServiceMenu(@RequestBody List<String> ids) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (!ids.isEmpty()) {
			boolean fag = serviceMenuService.batchDelServiceMenu(ids);
			if (fag) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("删除服务商菜单失败");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("删除条件不能为空");
		}
		return result;
	}

	/**
	 * @Title: getServiceMenu
	 * @Description: 条件查询服务商菜单信息
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/getServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "条件查询服务商菜单信息", notes = "输入查询条件")
	public ResultMsg<ServiceMenu> getServiceMenu(@RequestBody ServiceMenu menu) {
		ResultMsg<ServiceMenu> result = new ResultMsg<ServiceMenu>();
		if (menu != null) {
			EntityWrapper<ServiceMenu> entity = new EntityWrapper<ServiceMenu>(menu);
			ServiceMenu m = serviceMenuService.selectOne(entity);
			if (m != null) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(m);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("为获取到服务商菜单信息");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("查询条件不能为空");
		}
		return result;
	}

	/**
	 * @Title: selectPageServiceMenu
	 * @Description: 分页查询服务商菜单信息
	 * @author luojing
	 * @param @param menu
	 * @param @return 参数说明
	 * @return ResultMsg<ServiceMenu> 返回类型
	 * @throws @date 2018年7月11日
	 */
	@RequestMapping(value = "/auth/selectPageServiceMenu", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "分页查询服务商菜单信息", notes = "输入查询条件")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段:ordertime-订单时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", dataType = "string", paramType = "query") })
	public ResultMsg<Page<ServiceMenu>> selectPageServiceMenu(@RequestBody ServiceMenu menu) {
		ResultMsg<Page<ServiceMenu>> result = new ResultMsg<Page<ServiceMenu>>();
		// 获取分页参数
		Page<ServiceMenu> page = new PageFactory<ServiceMenu>().defaultPage();
		Page<ServiceMenu> pg = new Page<ServiceMenu>();
		if (menu != null) {
			EntityWrapper<ServiceMenu> entity = new EntityWrapper<ServiceMenu>(menu);
			pg = serviceMenuService.selectPage(page, entity);
		} else {
			pg = serviceMenuService.selectPage(page);
		}
		if (pg != null) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(pg);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("为获取到服务商菜单信息");
		}
		return result;
	}

	/**
	 * @Title: selectTreeMenuByAgentId
	 * @Description: 根据经纪人ID查询菜单
	 * @author luojing
	 * @param @param agentId
	 * @param @return 参数说明
	 * @return ResultMsg<List<Tree<ServiceMenu>>> 返回类型
	 * @throws @date 2018年7月12日
	 */
	@RequestMapping(value = "/auth/selectTreeMenuByAgentId")
	@ApiOperation(httpMethod = "POST", value = "根据经纪人ID查询菜单", notes = "根据经纪人ID查询菜单")
	public ResultMsg<List<Tree<ServiceMenu>>> selectTreeMenuByAgentId() {
		ResultMsg<List<Tree<ServiceMenu>>> result = new ResultMsg<List<Tree<ServiceMenu>>>();
		TrwTAgent agent =ShiroKit.getUser();
	 
		List<Tree<ServiceMenu>> list = serviceMenuService
				.selectTreeMenuByAgentId(agent.getId());
		if (!list.isEmpty()) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(list);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("未获取到菜单消息");
		}
		return result;
	}

	/**
	 * @Title: selectMenuTreeByRoleId
	 * @Description: 根据角色ID查询菜单树
	 * @author luojing
	 * @param @param roleId
	 * @param @return 参数说明
	 * @return ResultMsg<List<Tree<ServiceMenu>>> 返回类型
	 * @throws @date 2018年7月12日
	 */
	@RequestMapping(value = "/auth/selectMenuTreeByRoleId")
	@ApiOperation(httpMethod = "POST", value = "根据角色ID查询菜单树", notes = "根据角色ID查询菜单树")
	public ResultMsg<List<Tree<ServiceMenu>>> selectMenuTreeByRoleId(@RequestParam("roleId") Integer roleId) {
		ResultMsg<List<Tree<ServiceMenu>>> result = new ResultMsg<List<Tree<ServiceMenu>>>();
		if (roleId != null && roleId > 0) {
			List<Tree<ServiceMenu>> list = serviceMenuService.selectMenuTreeByRoleId(roleId);
			if (!list.isEmpty()) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(list);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("未获取到菜单消息");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("角色编号不能为空");
		}
		return result;
	}
	
	
	/**
	 * @Title: selectMenuTreeByRoleId
	 * @Description: 根据角色ID查询菜单树
	 * @author luojing
	 * @param @param roleId
	 * @param @return 参数说明
	 * @return ResultMsg<List<Tree<ServiceMenu>>> 返回类型
	 * @throws @date 2018年7月12日
	 */
	@RequestMapping(value = "/auth/getMenuTreeByRoleId")
	@ApiOperation(httpMethod = "POST", value = "根据角色ID查询菜单树", notes = "根据角色ID查询菜单树")
	public ResultMsg<List<Integer>> getMenuTreeByRoleId(@RequestParam("roleId") Integer roleId) {
		ResultMsg<List<Integer>> result = new ResultMsg<>();
		if (roleId != null && roleId > 0) {
			List<Integer> list = serviceMenuService.selectRoleMenuByRoleId(roleId);
			if (!list.isEmpty()) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(list);
			} else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("未获取到菜单消息");
			}
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("角色编号不能为空");
		}
		return result;
	}	

	/**
	 * @Title: selectMenuTree
	 * @Description: 查询所有菜单树
	 * @author luojing
	 * @param @return 参数说明
	 * @return Tree<ServiceMenu> 返回类型
	 * @throws @date 2018年7月12日
	 */
	@RequestMapping(value = "/auth/selectMenuTree")
	@ApiOperation(httpMethod = "POST", value = "查询所有菜单树", notes = "查询所有菜单树")
	public ResultMsg<List<Tree<ServiceMenu>>> selectMenuTree() {
		ResultMsg<List<Tree<ServiceMenu>>> result = new ResultMsg<List<Tree<ServiceMenu>>>();
		List<Tree<ServiceMenu>> list = serviceMenuService.selectMenuTree();
		if (!list.isEmpty()) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(list);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("未获取到菜单消息");
		}
		return result;
	}

}
