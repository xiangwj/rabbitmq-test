package org.rabbitmq.config;
import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
	@Autowired
	RabbitTemplate rabbitTemplate;
	@PostConstruct
	public void init() {
		this.rabbitTemplate.setConfirmCallback(this);
		this.rabbitTemplate.setMandatory(true);
		this.rabbitTemplate.setReturnCallback(this);
	}
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if(ack) {
			log.info("^^交换已经收到ID为{}的消息",correlationData.getId());
		}else {
			log.info("!!交换已经未收到ID为{}的消息,原因是{}",correlationData.getId(),cause);
		}
		
	}
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		log.error("!!!!!!!!!!!!!!!!!!消息:{}被回退了,交换机{},路由键{}",new String(message.getBody()),exchange,routingKey);
		
	}

}
