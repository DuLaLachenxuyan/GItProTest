package com.trw.feign.hystrix;

import org.springframework.stereotype.Service;

import com.trw.feign.UserFeignApi;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.model.TrwTUser;
import com.trw.vo.ResultMsg;

@Service
public class UserFeignHystrix implements UserFeignApi {

	@Override
	public ResultMsg<TrwTUser> getUser(String userId) {
		return null;
	}

	@Override
	public ResultMsg<TrwTAccount> getAccountById(String userId) {
		return null;
	}

	@Override
	public ResultMsg<String> addAccount(TrwTAccount acc) {
		return null;
	}

	@Override
	public ResultMsg<String> addAccDetail(TrwTAccDetail acc) {
	 
		return null;
	}
	
	@Override
	public ResultMsg<Integer> getEnshrineNum(String productId) {
		return null;
	}
	
	
}
