package com.trw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.trw.constant.Constant;
import com.trw.model.TrwTUserCard;
import com.trw.service.UserCardService;
import com.trw.util.StrKit;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户身份证信息 前端控制器
 * </p>
 *
 * @author gongzhen123
 * @since 2018-07-09
 */
@RestController
public class UserCardController extends BaseController {
	
	@Autowired
	private UserCardService userCardService;
	
	/**
	* @Title: getUserCard 
	* @Description: 条件获取身份证信息
	* @author luojing
	* @param @return  参数说明 
	* @return ResultMsg<TrwTUserCard> 返回类型 
	* @throws 
	* @date 2018年7月9日
	 */
	@RequestMapping(value="/guest/getUserCard")
	@ApiOperation(httpMethod = "POST", value = "条件获取身份证信息", notes = "条件获取身份证信息")
	public ResultMsg<TrwTUserCard> getUserCard(){
		ResultMsg<TrwTUserCard> result = new ResultMsg<TrwTUserCard>();
		TokenData tokenData = getTokenData();
		if(tokenData == null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("请先登录");
			return result;
		}
		TrwTUserCard card = new TrwTUserCard();
		card.setUserid(tokenData.getUserId());
		EntityWrapper<TrwTUserCard> entityWrapper = new EntityWrapper<TrwTUserCard>(card);
		TrwTUserCard userCard = userCardService.selectOne(entityWrapper);
		if(userCard==null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("为获取身份证信息");
		}else {
			result.setCode(Constant.CODE_SUCC);
			result.setData(userCard);
		}
		return result;
		
	}
	
	/**
	* @Title: insertUserCard 
	* @Description: 添加身份证信息（绑定身份证）
	* @author luojing
	* @param @param card
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年7月9日
	 */
	@RequestMapping(value = "/guest/insertUserCard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "添加身份证信息（绑定身份证）", notes = "添加身份证信息（绑定身份证）")
	public ResultMsg<String> insertUserCard(@RequestBody TrwTUserCard card){
		ResultMsg<String> result = new ResultMsg<String>();
		// 判断是否登陆
		TokenData td = getTokenData();
		if (td == null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("请先登录");
			return result;
		}
		if(card == null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("身份证信息不能为空");
		}else {
			card.setUserid(td.getUserId());
			if(StrKit.isEmpty(card.getRealname())) {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("真实姓名不能为空");
			}else if(StrKit.isEmpty(card.getCarNumber())) {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("身份证号码不能为空");
			}else if(StrKit.isEmpty(card.getPositive()) && StrKit.isEmpty(card.getNegative())) {
				result.setCode(Constant.CODE_FAIL);
				result.setMsg("身份证存放地址不能为空");
			}else {
				boolean fag = userCardService.insertOrUpdate(card);
				if(fag) {
					result.setCode(Constant.CODE_SUCC);
				}else {
					result.setCode(Constant.CODE_FAIL);
					result.setMsg("绑定身份证失败");
				}
			}
		}
		return result;
	}	
}

