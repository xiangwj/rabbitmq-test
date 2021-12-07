package org.rabbitmq.dead;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class ProducerDead {
	public static String NOMAL_EXCHANGE = "nomal-exchange";
	public static String NOMAL_QUEUE_ROUTE = "nomal-999";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		AMQP.BasicProperties properties = new AMQP.BasicProperties()
				.builder().expiration("10000").build();
		
		
		for(int i=1;i<11;i++) {
			String message="info-"+i;
			channel.basicPublish(NOMAL_EXCHANGE, NOMAL_QUEUE_ROUTE, properties, message.getBytes("UTF-8"));
			
		}
	}
}
