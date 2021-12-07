package org.rabbitmq.one;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {
	public void receive(String queuename,String ipaddress,String username,String password) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(ipaddress);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();

		channel.basicConsume(queuename, (consumerTag, msg)->{
			System.out.println("收到消息：|"+consumerTag+":"+new String(msg.getBody()));
		}, consumerTag->{
			System.out.println("取消消息：|"+consumerTag);
		});

	}
	
}
