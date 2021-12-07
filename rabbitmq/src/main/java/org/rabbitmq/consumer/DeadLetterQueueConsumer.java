package org.rabbitmq.consumer;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class DeadLetterQueueConsumer {
	@RabbitListener(queues = {"QD"} )
	public void receiveD(Message message,Channel channel) throws UnsupportedEncodingException {
		String msg = new String(message.getBody(),"UTF-8");
		log.info("当前时间{},收到的消息{}",new Date().toString(),  msg);
	}
}
