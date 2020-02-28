package com.trw.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceFundsDetail;
import com.trw.model.TrwTAgent;
import com.trw.service.ServiceFundsDetailService;
import com.trw.util.PageKit;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @ClassName: ServiceFundsDetailController 
* @Description: 服务中心资金明细前端控制器
* @author luojing
* @date 2018年7月16日 下午3:13:17 
*
 */
@RestController	
public class ServiceFundsDetailController extends BaseController {
	
	@Autowired
	private ServiceFundsDetailService fundsDetailService;
	
	/**
	* @Title: selectPageFundsDetail 
	* @Description: 分页查询服务中心资金明细
	* @author luojing
	* @param @param detail
	* @param @return  参数说明 
	* @return ResultMsg<Page<ServiceFundsDetail>> 返回类型 
	* @throws 
	* @date 2018年7月16日
	 */
	@RequestMapping(value = "/auth/selectPageFundsDetail",consumes= {"application/json"})
	@ApiOperation(httpMethod = "POST", value = "分页查询服务中心资金明细", notes = "输入查询条件")
	public ResultMsg<Page<ServiceFundsDetail>> selectPageFundsDetail(@RequestBody PageKit<ServiceFundsDetail> param){
		ResultMsg<Page<ServiceFundsDetail>> result = new ResultMsg<Page<ServiceFundsDetail>>();
		TrwTAgent agent =ShiroKit.getUser();
		 
		if(param.getParam() == null) {
			param.setParam(new ServiceFundsDetail());
		}
		if(StrKit.isEmpty(param.getParam().getUserId())) {
			param.getParam().setUserId(agent.getId());
		}
		// 获取分页参数
		Page<ServiceFundsDetail> page =new Page<>();
		page.setLimit(param.getLimit());
		page.setCurrent(param.getPageNum());
		EntityWrapper<ServiceFundsDetail> wrapper = new EntityWrapper<ServiceFundsDetail>(param.getParam());
		Page<ServiceFundsDetail> pg = fundsDetailService.selectPage(page, wrapper);
		if (pg != null) {
			result.setCode(Constant.CODE_SUCC);
			result.setData(pg);
		} else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("未获取到资金明细");
		}
		return result;
	}
	
	/**
	* @Title: getFundsDetail 
	* @Description: 查询服务中心资金明细详详情
	* @author luojing
	* @param @param detail
	* @param @return  参数说明 
	* @return ResultMsg<Page<ServiceFundsDetail>> 返回类型 
	* @throws 
	* @date 2018年7月16日
	 */
	@RequestMapping(value = "/auth/getFundsDetail", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "查询服务中心资金明细详情", notes = "根据ID查询")
	public ResultMsg<ServiceFundsDetail> getFundsDetail(@RequestBody ServiceFundsDetail detail){
		ResultMsg<ServiceFundsDetail> result = new ResultMsg<ServiceFundsDetail>();
		if(detail!=null) {
			EntityWrapper<ServiceFundsDetail> wrapper = new EntityWrapper<ServiceFundsDetail>(detail);
			ServiceFundsDetail d = fundsDetailService.selectOne(wrapper);
			if( d!=null) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(d);
			}else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("为查询到资金明细详详情");
			}
		}else{
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("资金明细参数不能为空");
		}
		return result;
	}
	
	/**
	* @Title: addFundsDetail 
	* @Description: 添加服务中心资金明细
	* @author luojing
	* @param @param detail
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月17日
	 */
	@RequestMapping(value = "/auth/addFundsDetail", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "添加服务中心资金明细", notes = "添加服务中心资金明细")
	public ResultMsg<String> addFundsDetail(@RequestBody ServiceFundsDetail detail){
		ResultMsg<String> result = new ResultMsg<String>();
		TrwTAgent agent =ShiroKit.getUser();
	 
		if(detail != null) {
			detail.setUserId(agent.getId());
			detail.setFundsId(ToolUtil.generateId("LS"));
			if(fundsDetailService.insert(detail)) {
				result.setCode(Constant.CODE_SUCC);
			}else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("资金明细信息添加失败");
			}
		}else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("资金明细信息不能为空");
		}
		return result;
	}
	
	@RequestMapping(value = "/auth/updateFundsDetail", consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "修改服务中心资金明细", notes = "修改服务中心资金明细")
	public ResultMsg<String> updateFundsDetail(@RequestBody ServiceFundsDetail detail){
		ResultMsg<String> result = new ResultMsg<String>();
		if(detail != null) {
			if(fundsDetailService.updateById(detail)) {
				result.setCode(Constant.CODE_SUCC);
			}else {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("资金明细信息修改失败");
			}
		}else {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("资金明细信息不能为空");
		}
		return result;
	}
	
}

