package com.trw.feign.hystrix;

import org.springframework.stereotype.Service;

import com.trw.feign.FileFeignApi;

@Service
public class FileFeignHystrix implements FileFeignApi {

	@Override
	public String deleteFile(String imgs) {
		return "删除文件失败";
	}

	@Override
	public void updateFile(String imgs,String userId) {
		
	}

}
