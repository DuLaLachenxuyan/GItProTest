package com.trw.mq;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.trw.constant.Constant;
import com.trw.feign.ScoreFeignApi;
import com.trw.model.TrwTMsg;
import com.trw.service.IMsgService;
import com.trw.service.impl.SmsService;
import com.trw.util.StrKit;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ScoreMsg;

import lombok.extern.log4j.Log4j2;



@Component
@Log4j2
public class Consumer {
	@Autowired
	private SmsService smsService;
	@Autowired
	private IMsgService msgService;
	
	@Autowired
	private ScoreFeignApi scoreApi;
	
	
	/**
	 * 
	* @Title: smsConSumer 
	* @Description: 消息消费者
	* @author jianglingyun
	* @param @param message
	* @param @param channel
	* @param @throws IOException  参数说明 
	* @return void 返回类型 
	* @throws 
	* @date 2018年8月11日
	 */
	@RabbitListener(queues = "trw_sms_queue")
	public void smsConSumer(Message message,Channel channel) throws IOException {
		// 保存数据库
		TrwTMsg bean = new TrwTMsg();
		bean.setCreatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		NoticeMsg msg =JSON.parseObject(new String(message.getBody()),NoticeMsg.class );
		String template = Constant.SMSTEMPLATEMAP.get(msg.getMsgId());
		JSONObject smsJson = JSON.parseObject(msg.getParam());
		Set<String> keys = smsJson.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			// 获得key
			String key = it.next();
			String value = smsJson.getString(key);
			template = template.replace("${" + key + "}", value);
		}
		bean.setMsgtext(template);
		bean.setUserid(msg.getUserId());
		msgService.insert(bean);
		if (!StrKit.isBlank(msg.getNeedSMS())) { // 不为空
			smsService.sendNotic(msg.getMsgId(), msg.getPhone(), smsJson);
		}
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	
	@RabbitListener(queues = "trw_score_queue")
	public void scoreConsumer(Message message,Channel channel) throws IOException {
		ScoreMsg msg =JSON.parseObject(new String(message.getBody()),ScoreMsg.class );
	    //修改积分
		try {
			scoreApi.modifyScore(msg.getFaciId(), msg.getRuleId());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}

	}

}