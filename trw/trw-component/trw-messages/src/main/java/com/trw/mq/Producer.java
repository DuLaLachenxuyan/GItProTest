package com.trw.mq;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import com.trw.vo.NoticeMsg;
import com.trw.vo.ScoreMsg;

@Component
public class Producer {
	@Resource
	private RabbitTemplate rabbitTemplate;

	public void sendMsg(String content) {
		 // 获取CorrelationData对象
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

		rabbitTemplate.convertAndSend("trw_sms_queue", (Object)content, correlationData);
	}

	public void sendMsg(NoticeMsg msg) {
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend("trw_sms_queue", msg,correlationData);
	}

	public void scoreMsg(ScoreMsg msg) {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		rabbitTemplate.convertAndSend("trw_score_queue", msg,correlationData);
	}

}