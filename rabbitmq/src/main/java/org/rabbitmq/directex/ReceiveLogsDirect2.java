package org.rabbitmq.directex;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

public class ReceiveLogsDirect2 {
	public static final String EXCHANGE_NAME="direct_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		channel.queueDeclare("disk", false, false, false, null);
		channel.queueBind("disk", EXCHANGE_NAME, "error");
		channel.basicConsume("disk", true, (consumerTag, message)->{
			System.out.println("ReceiveLogsDirect2接到的消息:"+new String(message.getBody()));
		},consumerTag->{});
	}

}
