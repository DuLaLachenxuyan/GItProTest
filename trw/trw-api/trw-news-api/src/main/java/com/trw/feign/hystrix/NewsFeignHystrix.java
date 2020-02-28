package com.trw.feign.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.trw.feign.NewsFeignApi;
import com.trw.model.TNews;
import com.trw.vo.ResultMsg;

@Component
public class NewsFeignHystrix implements NewsFeignApi{
	@Override
	public ResultMsg<List<TNews>> selectMainNews() {
		ResultMsg<List<TNews>> rm = new ResultMsg<>();
		return rm;
	}

}
