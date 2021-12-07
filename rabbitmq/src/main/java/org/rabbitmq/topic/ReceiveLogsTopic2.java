package org.rabbitmq.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class ReceiveLogsTopic2 {
	public static final String EXCHANGE_NAME="topic_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		String queueName ="Q2";
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
		channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
		System.out.println("等待接收消息.........");
		channel.basicConsume(queueName, true, (consumerTag, message)->{
			System.out.println("接收队列:"+queueName+" 绑定键:"+message.getEnvelope().getRoutingKey()+" message:"+new String(message.getBody(),"UTF-8"));
		},consumerTag->{});
	}
}
