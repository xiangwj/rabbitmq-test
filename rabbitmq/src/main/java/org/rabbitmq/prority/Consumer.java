package org.rabbitmq.prority;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

public class Consumer {
	private static final String QUEUE_NAME="prority-test";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		
		Map<String, Object> params = new HashMap();
		params.put("x-max-priority", 10);
		channel.queueDeclare(QUEUE_NAME, true, false, false, params);
		
		System.out.println("消费者启动等待消费..............");
		channel.basicConsume(QUEUE_NAME,true,(String consumerTag, Delivery message)->{
			 		String receivedMessage = new String(message.getBody());System.out.println("接收到消息:"+receivedMessage);
				},
				(String consumerTag)->{
					System.out.println("消费者无法消费消息时调用，如队列被删除");
				});
	}
}
