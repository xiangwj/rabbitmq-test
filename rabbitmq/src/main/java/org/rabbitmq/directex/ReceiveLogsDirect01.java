package org.rabbitmq.directex;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

public class ReceiveLogsDirect01 {
	public static final String EXCHANGE_NAME="direct_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		channel.queueDeclare("console", false, false, false, null);
		channel.queueBind("console", EXCHANGE_NAME, "info");
		channel.queueBind("console", EXCHANGE_NAME, "warning");
		channel.basicConsume("console", true, (consumerTag, message)->{
			System.out.println("ReceiveLogsDirect01接到的消息:"+new String(message.getBody()));
		},consumerTag->{});
	}

}
