package org.rabbitmq.controller;

import java.util.Date;

import org.rabbitmq.config.DelayedQueueConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@GetMapping("/sendMsg/{message}")
	public void sendMsg(@PathVariable("message") String message) {
		rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s");
		rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s:"+message);
		log.info("当前时间{},发送一条信息给两个队列{}",new Date().toString(),message);
		
	}
	@GetMapping("/sendExpirationMsg/{message}/{ttltime}")
	public void sendMsg(@PathVariable("message")  String message,@PathVariable("ttltime") String ttltime) {
		rabbitTemplate.convertAndSend("X","XC", message, new MessagePostProcessor(){

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setExpiration(ttltime);
				return message;
			}
			
		});
		log.info("当前时间{},发送一条时长为{}毫秒的信息给QC{}",new Date().toString(),ttltime,message);
	}
	@GetMapping("/sendPlugMsg/{message}/{ttltime}")
	public void sendPlugMsg(@PathVariable("message")  String message,@PathVariable("ttltime") Integer ttltime) {
		rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,DelayedQueueConfig.DELAYED_ROUTE_KEY, message, new MessagePostProcessor(){

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(ttltime);
				return message;
			}
			
		});
		log.info("当前时间{},发送一条时长为{}毫秒的信息给QC{}",new Date().toString(),ttltime,message);
	}

}
