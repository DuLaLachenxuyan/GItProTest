package com.trw.feign.hystrix;

import org.springframework.stereotype.Service;

import com.trw.feign.MsgFeignApi;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ScoreMsg;

@Service
public class MsgFeignHystrix implements MsgFeignApi{
	@Override
	public void sendMessage(NoticeMsg msg) {
	}

	@Override
	public void scoreMsg(ScoreMsg msg) {
		
	}

	 

}
