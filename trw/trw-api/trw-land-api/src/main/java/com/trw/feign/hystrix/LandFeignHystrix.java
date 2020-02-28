package com.trw.feign.hystrix;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.trw.feign.LandFeignApi;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;
import com.trw.vo.ResultMsg;

@Service
public class LandFeignHystrix implements LandFeignApi {
	
	
	@Override
	public ResultMsg<Map<String,Object>> selectFaciLand(String faciid) {
		ResultMsg<Map<String,Object>> resultMsg=new ResultMsg<>();
		resultMsg.setMsg("0000");
		return resultMsg;
	}
	
	@Override
	public ResultMsg<TrwTLand> getLandById(String productid) {
		ResultMsg<TrwTLand> resultMsg=new ResultMsg<>();
		resultMsg.setMsg("0000");
		return resultMsg;
	}
	
	@Override
	public Boolean saveAllocal(TrwTAllocation entity) {
		return false;
	}
	
	@Override
	public Boolean saveAllAllotAgent(String landsID, String angentID) {
		return false;
	}
	
	@Override
	public ResultMsg<String> addLandMarkFeedback(Map<String, String> map) {
		ResultMsg<String> resultMsg=new ResultMsg<>();
		resultMsg.setMsg("0000");
		return resultMsg;
	}
	
	
	@Override
	public ResultMsg<String> updateByid(TrwTLand trwTLand) {
		return null;
	}
	
}
