package org.rabbitmq.consumer;

import org.rabbitmq.config.ConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WarningConsumer {
	@RabbitListener(queues = {ConfirmConfig.WARNING_QUEUE_NAME})
	public void receiveWarningMsg(Message message) {
		String msg = new String(message.getBody());
		log.error("ooo报警发现不可路由消息：{}", msg);
	}
	
}
