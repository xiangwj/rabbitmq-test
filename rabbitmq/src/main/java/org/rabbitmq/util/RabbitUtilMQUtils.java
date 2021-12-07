package org.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitUtilMQUtils {
	public static String IPADDRESS="192.168.137.120";
	public static String USERNAME="admin";
	public static String PASSWORD="111111";
	public static String QUEUE_NAME="hello";
	public static Channel getChannel() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(IPADDRESS);
		connectionFactory.setUsername(USERNAME);
		connectionFactory.setPassword(PASSWORD);
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		return channel;
	}
}
