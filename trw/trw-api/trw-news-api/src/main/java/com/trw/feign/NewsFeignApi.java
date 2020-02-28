package com.trw.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trw.feign.hystrix.NewsFeignHystrix;
import com.trw.model.TNews;
import com.trw.vo.ResultMsg;

@FeignClient(value = "trw-news" ,fallback=NewsFeignHystrix.class)
public interface NewsFeignApi {
	@RequestMapping("/selectMainNews")
	ResultMsg<List<TNews>> selectMainNews();

}
