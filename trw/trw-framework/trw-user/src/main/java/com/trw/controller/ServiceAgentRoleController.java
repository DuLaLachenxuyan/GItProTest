package com.trw.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.trw.constant.Constant;
import com.trw.model.ServiceAgentRole;
import com.trw.service.ServiceAgentRoleService;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
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
 * @author luojing
 * @ClassName: ServiceAgentRoleController
 * @Description: 服务商用户角色表 前端控制器
 * @date 2018年7月11日 下午1:56:41
 */
@RestController
public class ServiceAgentRoleController extends BaseController {
    
    @Autowired
    private ServiceAgentRoleService serviceAgentRoleService;
    
    /**
     * @param @param  roleMenu
     * @param @return 参数说明
     * @return ResultMsg<String> 返回类型
     * @throws
     * @Title: addServiceAgentRole
     * @Description: 为用户添加角色信息
     * @author luojing
     * @date 2018年7月11日
     */
    @RequestMapping(value = "/auth/addServiceAgentRole", consumes = {"application/json"})
    @ApiOperation(httpMethod = "POST", value = "为用户添加角色信息", notes = "输入用户和角色信息")
    public ResultMsg<String> addServiceAgentRole(@RequestBody ServiceAgentRole agentRole) {
        ResultMsg<String> result = new ResultMsg<String>();
        if (agentRole != null) {
        	agentRole.setArId(ToolUtil.generateId());
            boolean fag = serviceAgentRoleService.insert(agentRole);
            if (fag) {
                result.setCode(Constant.CODE_SUCC);
            } else {
                result.setCode(Constant.CODE_FAIL);
                result.setMsg("角色权限添加失败");
            }
        } else {
            result.setCode(Constant.CODE_FAIL);
            result.setMsg("角色权限信息不能为空");
        }
        return result;
    }
    
    /**
     * @param @param  roleMenu
     * @param @return 参数说明
     * @return ResultMsg<String> 返回类型
     * @throws
     * @Title: updateServiceAgentRole
     * @Description: 为用户修改角色信息
     * @author luojing
     * @date 2018年7月11日
     */
    @RequestMapping(value = "/auth/updateServiceAgentRole", consumes = {"application/json"})
    @ApiOperation(httpMethod = "POST", value = "为用户修改角色信息", notes = "输入用户ID和角色ID")
    public ResultMsg<String> updateServiceRoleMenu(@RequestBody ServiceAgentRole roleMenu) {
        ResultMsg<String> result = new ResultMsg<String>();
        if (roleMenu != null) {
            //根据菜单ID和角色ID删除
            EntityWrapper<ServiceAgentRole> wrapper = new EntityWrapper<ServiceAgentRole>(roleMenu);
            boolean fag = serviceAgentRoleService.delete(wrapper);
            if (fag) {
                result.setCode(Constant.CODE_SUCC);
            } else {
                result.setCode(Constant.CODE_FAIL);
                result.setMsg("角色权限修改失败");
            }
        } else {
            result.setCode(Constant.CODE_FAIL);
            result.setMsg("角色权限信息不能为空");
        }
        return result;
    }
    
    /**
     * @param @param  roleMenu
     * @param @return 参数说明
     * @return ResultMsg<String> 返回类型
     * @throws
     * @Title: batchUpdateServiceAgentRole
     * @Description: 为用户批量修改角色信息
     * @author luojing
     * @date 2018年7月11日
     */
    @RequestMapping(value = "/auth/batchUpdateServiceAgentRole", consumes = {"application/json"})
    @ApiOperation(httpMethod = "POST", value = "为用户批量修改角色信息", notes = "输入用户ID和角色ID")
    public ResultMsg<String> batchUpdateServiceRoleMenu(@RequestBody List<ServiceAgentRole> list) {
        ResultMsg<String> result = new ResultMsg<String>();
        if (!list.isEmpty()) {
            serviceAgentRoleService.batchUpdateServiceAgentRole(list);
            result.setCode(Constant.CODE_SUCC);
        } else {
            result.setCode(Constant.CODE_FAIL);
            result.setMsg("角色权限信息不能为空");
        }
        return result;
    }
    
    @RequestMapping(value = "/selectByAgentid")
    @ApiOperation(httpMethod = "POST", value = "根据登录的用户查询对应的角色", notes = "根据登录的用户查询对应的角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "经纪人id", required = true, dataType = "string", paramType = "query")
    })
    public ResultMsg<ServiceAgentRole> selectByAgentid(@RequestParam("id") String id) {
        ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectByAgentid(id);
        ResultMsg<ServiceAgentRole> resultMsg = new ResultMsg<>();
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(serviceAgentRole);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/updateAgentRole")
    @ApiOperation(httpMethod = "POST", value = "服务商修改员工角色", notes = "服务商修改员工角色")
    public ResultMsg<String> updateAgentRole(@RequestParam("agentId") String agentId,@RequestParam("roleId") String roleId){
    	 ResultMsg<String> result = new ResultMsg<String>();
    	 
    	 //获取经纪人角色主键
    	 Wrapper<ServiceAgentRole> wrapper = new EntityWrapper<ServiceAgentRole>().eq("agentId", agentId);
    	 ServiceAgentRole serviceAgentRole = serviceAgentRoleService.selectOne(wrapper);
    	 String arId = serviceAgentRole.getArId();
    	 
    	 if (arId == null) {
    		 result.setCode(Constant.CODE_FAIL);
             result.setMsg("经纪人角色为空");
             return result;
		}
    	 ServiceAgentRole entity = new  ServiceAgentRole();
    	 entity.setArId(arId);
    	 entity.setAgentId(agentId);
    	 entity.setRoleId(roleId);
    	 boolean flg = serviceAgentRoleService.updateById(entity);
    	 
		 if (flg) {
             result.setCode(Constant.CODE_SUCC);
             result.setMsg("角色修改成功");
         } else {
             result.setCode(Constant.CODE_FAIL);
             result.setMsg("角色修改失败");
             return result;
         }
		return result;
    }
    
    
}

