package org.rabbitmq.topic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.rabbitmq.util.RabbitUtilMQUtils;

import com.rabbitmq.client.Channel;

public class EmitLogTopic {
	public static final String EXCHANGE_NAME="topic_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
		Channel channel = RabbitUtilMQUtils.getChannel();
		Map<String,String> bindKeyMap = new HashMap<>();
		bindKeyMap.put("quick.orange.rabbit", "被队列 Q1Q2 接收到");
		bindKeyMap.put("lazy.orange.elephant", "被队列 Q1Q2 接收到");
		bindKeyMap.put("quick.orange.fox", "被队列 Q1 接收到");
		bindKeyMap.put("lazy.brown.fox", "被队列 Q2 接收到");
		bindKeyMap.put("lazy.pink.rabbit", "虽然满足两个绑定但只被队列 Q2 接收一次");
		bindKeyMap.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收到会被丢弃");
		bindKeyMap.put("quick.orange.male.rabbit", "是四个单词不匹配任何绑定会被丢弃");
		bindKeyMap.put("azy.orange.male.rabbit", "是四个单词但匹配 Q2");
		
		for(Map.Entry<String, String> entry:bindKeyMap.entrySet()) {
			channel.basicPublish(EXCHANGE_NAME, entry.getKey(), null, entry.getValue().getBytes("UTF-8"));
			System.out.println("发消息:"+entry.getKey()+"\t"+entry.getValue());
		}
		
	}
}
