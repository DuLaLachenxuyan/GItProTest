package com.trw.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.service.IAccDetailService;
import com.trw.service.IAccountService;
import com.trw.util.StrKit;
import com.trw.vo.AccountVo;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  资金账户controller
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-16
 */
@RestController
public class AccountController extends BaseController {
	@Autowired
	IAccountService accountservice;
	@Autowired
	IAccDetailService accDetailService;

	@RequestMapping(value = "/guest/getAccount")
	@ApiOperation(httpMethod = "POST",value = "流水账单条件查询", notes = "流水账单条件查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "airtime", value = "开始时间",dataType="string", paramType = "query"),
		@ApiImplicitParam(name = "endtime", value = "结束时间",dataType="string", paramType = "query"),
		@ApiImplicitParam(name = "fundFlow", value = "资金流向（01-收入,02-支出）",dataType="string", paramType = "query"),
		@ApiImplicitParam(name = "fundType", value = "资金类型(01-普通资金,02-保证金)",dataType="string", paramType = "query"),		
		@ApiImplicitParam(name = "sort", value = "排序字段:creattime-发布时间",dataType="string", paramType = "query"),	
		@ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)",dataType="string", paramType = "query"),
	})
	public ResultMsg<Map<String, Object>> getAccount(HttpServletRequest req){
		ResultMsg<Map<String, Object>> msg = new ResultMsg<>();
		Map<String,String> param =getParamMapFromRequest(req);
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		if (tokenData == null) {
			msg.setMsg("请先登录");
			msg.setCode(Constant.CODE_FAIL);
			return msg;
		}
		String userId = tokenData.getUserId();
		param.put("userId", userId);
		Page<TrwTAccDetail> page = new PageFactory<TrwTAccDetail>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		List<AccountVo> list = accountservice.selectAccDetail(page,param);
		map.put("page", page);
		map.put("list", list);
		msg.setCode(Constant.CODE_SUCC);
		msg.setData(map);
		return msg;
	}
	
	/**
	* @Title: getAccountById 
	* @Description: 根据ID查询资金账户
	* @author luojing
	* @param @param userId
	* @param @return  参数说明 
	* @return ResultMsg<TrwTAccount> 返回类型 
	* @throws 
	* @date 2018年7月4日
	 */
	@RequestMapping(value="/guest/getAccountById",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(httpMethod = "GET",value = "根据ID查询资金账户", notes = "根据ID查询资金账户")
	public ResultMsg<TrwTAccount> getAccountById(@RequestParam("userId") String userId){
		ResultMsg<TrwTAccount> result = new ResultMsg<TrwTAccount>();
		TokenData tokenData = getTokenData();
		if (tokenData == null) {
			result.setMsg("请先登录");
			result.setCode(Constant.CODE_FAIL);
			return result;
		}
		if(StrKit.isNotEmpty(userId)) {
			TrwTAccount acc = accountservice.selectById(userId);
			if(acc!=null) {
				result.setCode(Constant.CODE_SUCC);
				result.setData(acc);
			}else {
				result.setCode(Constant.CODE_FAIL);
				result.setData(null);
			}
		}else {
			result.setCode(Constant.CODE_FAIL);
			result.setData(null);
		}
		return result;
	}
	
	/**
	* @Title: addAccount 
	* @Description: 插入资金账户
	* @author luojing
	* @param @param acc
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月4日
	 */
	@RequestMapping(value="/guest/addAccount" ,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST",value = "插入资金账户", notes = "插入资金账户")
	public ResultMsg<String> addAccount(@RequestBody TrwTAccount acc){
		ResultMsg<String> result = new ResultMsg<>();
		TokenData tokenData = getTokenData();
		if (tokenData == null) {
			result.setMsg("请先登录");
			result.setCode(Constant.CODE_FAIL);
			return result;
		}
		if(acc!=null) {
			boolean fag = accountservice.insert(acc);
			if(fag) {
				result.setCode(Constant.CODE_SUCC);
			}else {
				result.setCode(Constant.CODE_FAIL);
			}
		}else {
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}
	
	/**
	* @Title: addAccDetail 
	* @Description: 插入资金账户明细
	* @author luojing
	* @param @param acc
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月4日
	 */
	@RequestMapping(value="/guest/addAccDetail",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST",value = "插入资金账户明细", notes = "插入资金账户明细")
	public ResultMsg<String> addAccDetail(@RequestBody TrwTAccDetail acc){
		ResultMsg<String> result = new ResultMsg<>();
		TokenData tokenData = getTokenData();
		if (tokenData == null) {
			result.setMsg("请先登录");
			result.setCode(Constant.CODE_FAIL);
			return result;
		}
		if(acc!=null) {
			boolean fag = accDetailService.insert(acc);
			if(fag) {
				result.setCode(Constant.CODE_SUCC);
			}else {
				result.setCode(Constant.CODE_FAIL);
			}
		}else {
			result.setCode(Constant.CODE_FAIL);
		}
		return result;
	}
	
	

}

