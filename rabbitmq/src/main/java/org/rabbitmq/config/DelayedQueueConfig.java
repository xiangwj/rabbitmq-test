package org.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DelayedQueueConfig {
	public static final String DELAYED_QUEUE_NAME="delayed.queue";
	public static final String DELAYED_EXCHANGE_NAME="delayed.exchange";
	public static final String DELAYED_ROUTE_KEY="delayed.routingkey";
	
	/*@Bean("delayedExchange")
	public CustomExchange delayedExchange() {
		Map<String,Object> args = new HashMap<>();
		args.put("x-delayed-type", "direct");
		return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,args);
		
		
	}*/
	@Bean("delayedExchange")
	public DirectExchange delayedExchange() {
		return new DirectExchange(DELAYED_EXCHANGE_NAME);
		
		
	}
	@Bean("delayedQueue")
	public Queue delayedQueue() {
		return new Queue(DELAYED_QUEUE_NAME);
	}
	/*@Bean
	@DependsOn({"delayedExchange","delayedQueue"})
	public Binding delayedQueueBindingDelayedExchange(
			@Qualifier("delayedQueue") Queue queue,
			@Qualifier("delayedExchange") CustomExchange exchange
			) {
		return  BindingBuilder.bind(queue).to(exchange).with(DELAYED_ROUTE_KEY).noargs();
	}*/
	@Bean
	@DependsOn({"delayedExchange","delayedQueue"})
	public Binding delayedQueueBindingDelayedExchange(
			@Qualifier("delayedQueue") Queue queue,
			@Qualifier("delayedExchange") DirectExchange exchange
			) {
		return  BindingBuilder.bind(queue).to(exchange).with(DELAYED_ROUTE_KEY);
	}	
}
