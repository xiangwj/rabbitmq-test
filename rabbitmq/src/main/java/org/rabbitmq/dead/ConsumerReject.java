package org.rabbitmq.dead;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class ConsumerReject {
	public static String NOMAL_EXCHANGE = "nomal-exchange";
	public static String DEAD_EXCHANGE = "dead-exchange";

	public static String NOMAL_QUEUE = "nomal-queue";
	public static String DEAD_QUEUE = "dead-queue";

	public static String NOMAL_QUEUE_ROUTE = "nomal-999";
	public static String DEAD_QUEUE_ROUTE = "dead-999";

	public static void main(String[] args) throws IOException, TimeoutException {

		Channel channel = RabbitUtilMQUtils.getChannel();

		channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
		channel.exchangeDeclare(NOMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

		channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
		channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_QUEUE_ROUTE);

		Map<String, Object> arguments = new HashMap<>();

		arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
		arguments.put("x-dead-letter-routing-key", DEAD_QUEUE_ROUTE);

		channel.queueDeclare(NOMAL_QUEUE, false, false, false, arguments);
		channel.queueBind(NOMAL_QUEUE, NOMAL_EXCHANGE, NOMAL_QUEUE_ROUTE);

		System.out.println("ConsumerReject 等待接收消息............");
		channel.basicConsume(NOMAL_QUEUE, false, (consumerTag, message) -> {
			String item = new String(message.getBody(), "UTF-8");
			if(item.equals("info-5")) {
				System.out.println("^^^ConsumerReject拒绝了:" + new String(message.getBody(), "UTF-8"));
				channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
			}else {
				System.out.println("Consumer01接收的消息是:" + new String(message.getBody(), "UTF-8"));
				channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
			}
		}, consumerTag -> {

		});

	}
}
