package com.trw.feign.hystrix;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.trw.feign.FaciFeignApi;
import com.trw.model.TrwTFaci;
import com.trw.vo.ResultMsg;

@Service
public class FaciFeignHystrix implements FaciFeignApi{
	

	@Override
	public ResultMsg<TrwTFaci> getFaciByLoc(String location) {
		ResultMsg<TrwTFaci> re = new ResultMsg<>();
		return re;
	}
	
	@Override
	public ResultMsg<Map<String, Object>> selectFaciList(String areaid) {
		ResultMsg<Map<String, Object>> re = new ResultMsg<>();
		return re;
	}
	
	
	@Override
	public ResultMsg<TrwTFaci> getFaciByfaciId(String faciId) {
		ResultMsg<TrwTFaci> re = new ResultMsg<>();
		return re;
	}

	 

}
