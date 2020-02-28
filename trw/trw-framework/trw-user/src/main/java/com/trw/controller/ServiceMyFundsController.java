package com.trw.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.trw.constant.Constant;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceMyFunds;
import com.trw.model.TrwTAgent;
import com.trw.service.ServiceMyFundsService;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ServiceMyFundsController
 * @Description: 服务中心资金前端控制器
 * @author luojing
 * @date 2018年7月16日 下午3:13:26
 *
 */
@RestController
public class ServiceMyFundsController extends BaseController {
	@Autowired
	private ServiceMyFundsService myFundsService;

	/**
	 * @Title: getFunds
	 * @Description: 查询经纪人账户信息
	 * @author luojing
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月16日
	 */
	@RequestMapping(value = "/auth/getAgentFunds")
	@ApiOperation(httpMethod = "POST", value = "查询经纪人账户信息", notes = "查询经纪人账户信息")
	public ResultMsg<ServiceMyFunds> getFunds() {
		ResultMsg<ServiceMyFunds> result = new ResultMsg<ServiceMyFunds>();
		TrwTAgent agent =ShiroKit.getUser();
		 
		// 根据经纪人ID查询资金
		ServiceMyFunds myFunds = new ServiceMyFunds();
		myFunds.setUserId(agent.getId());
		EntityWrapper<ServiceMyFunds> wrapper = new EntityWrapper<ServiceMyFunds>(myFunds);
		ServiceMyFunds funds = myFundsService.selectOne(wrapper);
		if (funds != null) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(funds);
		} else {
			result.setMsg("查询经纪人账户信息");
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}

	/**
	 * @Title: addAgentFunds
	 * @Description: 添加经纪人资金信息
	 * @author luojing
	 * @param @param funds
	 * @param @return 参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date 2018年7月16日
	 */
	@RequestMapping(value = "/auth/addAgentFunds", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "添加经纪人保证金", notes = "添加经纪人保证金")
	public ResultMsg<String> addAgentFunds(@RequestBody ServiceMyFunds funds) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (funds != null) {
			funds.setId(ToolUtil.generateId());
			if (myFundsService.insert(funds)) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setMsg("经纪人保证金添加失败");
				result.setCode(Constant.CODE_FAIL);
			}
		} else {
			result.setMsg("经纪人保证金信息不能为空");
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}

	/**
	* @Title: updateAgentFunds 
	* @Description: 修改经纪人余额信息
	* @author luojing
	* @param @param funds
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月16日
	 */
	@RequestMapping(value = "/auth/updateAgentFunds", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "修改经纪人余额信息", notes = "修改经纪人余额信息")
	public ResultMsg<String> updateAgentFunds(@RequestBody ServiceMyFunds funds) {
		ResultMsg<String> result = new ResultMsg<String>();
		if (funds != null) {
			if (myFundsService.updateById(funds)) {
				result.setCode(Constant.CODE_SUCC);
			} else {
				result.setMsg("经纪人余额信息修改失败");
				result.setCode(Constant.CODE_FAIL);
			}
		} else {
			result.setMsg("经纪人余额信息不能为空");
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}
}
