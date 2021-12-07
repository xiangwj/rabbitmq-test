package org.rabbitmq.directex;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.Channel;

public class DirectLogs {
	public static final String EXCHANGE_NAME="direct_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String message = scanner.next();
			channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
		}
	}
}
