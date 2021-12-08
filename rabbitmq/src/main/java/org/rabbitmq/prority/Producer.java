package org.rabbitmq.prority;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Producer {
	private static final String QUEUE_NAME="prority-test";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
		for(int i=0;i<10;i++) {
			String message = "info"+i;
			if(i==5) {
				channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
			}else {
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			}
		}
	}

}
