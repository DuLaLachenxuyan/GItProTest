package com.trw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "trw-filemanager")
public interface FileFeignApi {

	@RequestMapping(value = "/deleteFile", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteFile(String imgs);

	@RequestMapping(value = "/updateFile", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void updateFile(@RequestParam("imgs") String imgs, @RequestParam("userId") String userId);

}
