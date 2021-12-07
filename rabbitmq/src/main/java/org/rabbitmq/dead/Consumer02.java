package org.rabbitmq.dead;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.Channel;

public class Consumer02 {
	public static String DEAD_QUEUE = "dead-queue";

	public static void main(String[] args) throws IOException, TimeoutException {

		Channel channel = RabbitUtilMQUtils.getChannel();
		channel.basicConsume(DEAD_QUEUE, true, (consumerTag, message) -> {
			System.out.println("Consumer02接收的消息是:" + new String(message.getBody(), "UTF-8"));
		}, consumerTag -> {

		});

	}
}
