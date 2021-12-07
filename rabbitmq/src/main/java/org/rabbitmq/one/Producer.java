package org.rabbitmq.one;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	public void send(String queuename,String ipaddress,String username,String password,String message) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ipaddress);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		channel.queueDeclare(queuename, false, true, true, null);
		channel.basicPublish("", queuename, null, message.getBytes());
	}
	public void sendDefault(String message) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		channel.queueDeclare(RabbitUtilMQUtils.QUEUE_NAME, false, false, false, null);
		channel.basicPublish("", RabbitUtilMQUtils.QUEUE_NAME, null, message.getBytes());
	}
	
}
