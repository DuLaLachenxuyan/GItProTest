package com.trw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trw.vo.NoticeMsg;
import com.trw.vo.ScoreMsg;

@FeignClient(value = "trw-messages")
public interface MsgFeignApi {
	@RequestMapping(value="/sendMessage",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendMessage(NoticeMsg msg);

	@RequestMapping(value = "/scoreMsg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void scoreMsg(ScoreMsg msg);

}
