package org.rabbitmq.two;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

public class ReceiveWork01 {
	
	public void receive(String threadname) throws IOException, TimeoutException {
		RabbitUtilMQUtils.getChannel().basicConsume(RabbitUtilMQUtils.QUEUE_NAME, true, (consumerTag, msg)->{
			System.out.println("threadname:"+threadname+":收到消息：|"+consumerTag+":"+new String(msg.getBody()));
		}, consumerTag->{
			System.out.println("threadname:"+threadname+":取消消息：|"+consumerTag);
		});
	}
}
