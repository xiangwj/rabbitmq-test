package org.rabbitmq.controller;

import java.util.Date;

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
	

}
