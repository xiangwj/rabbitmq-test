package org.rabbitmq.controller;

import org.rabbitmq.config.ConfirmConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {
	@Autowired
	RabbitTemplate rabbitTemplate;
	@GetMapping("/sendMessage/{message}")
	public void sendMessage(@PathVariable("message") String message) {
		CorrelationData correlationData1 = new CorrelationData("1");
		rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTE_KEY, message,correlationData1);
		
		CorrelationData correlationData2 = new CorrelationData("2");
		rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTE_KEY+"123", message,correlationData2);
		log.info("发送的消息内容为:{}",message);
	}
}
