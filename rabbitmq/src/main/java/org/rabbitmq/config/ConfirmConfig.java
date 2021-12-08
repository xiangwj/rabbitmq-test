package org.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class ConfirmConfig {
	public static final String CONFIRM_EXCHANGE_NAME="confirm-exchange";
	public static final String CONFIRM_QUEUE_NAME="confirm-queue";
	public static final String CONFIRM_ROUTE_KEY="confirm-route-key";
	
	@Bean("confirmExchange")
	public DirectExchange confirmExchange() {
		return new DirectExchange(CONFIRM_EXCHANGE_NAME);
	}
	@Bean("confirmQueue")
	public Queue confirmQueue() {
		return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
	}
	@Bean
	@DependsOn({"confirmExchange","confirmQueue"})
	public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue queue,@Qualifier("confirmExchange")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTE_KEY);
	}
}
