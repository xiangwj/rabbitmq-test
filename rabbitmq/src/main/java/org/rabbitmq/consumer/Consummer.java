package org.rabbitmq.consumer;

import org.rabbitmq.config.ConfirmConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class Consummer {
	@RabbitListener(queues = {  ConfirmConfig.CONFIRM_QUEUE_NAME})
	public void receiveConfirmMessage(Message message) {
		log.info("接受到的消息{}",new String(message.getBody()));
	}

}
