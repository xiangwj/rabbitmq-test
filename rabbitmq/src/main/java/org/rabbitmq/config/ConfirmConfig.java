package org.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
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
	

	public static final String BACKUP_EXCHANGE_NAME="backup-exchange";
	public static final String BACKUP_QUEUE_NAME="backup-queue";
	public static final String WARNING_QUEUE_NAME = "warning-queue";
	
	
	@Bean("confirmExchange")
	public DirectExchange confirmExchange() {
		return  ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
	}
	@Bean("confirmQueue")
	public Queue confirmQueue() {
		return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
	}
	
	@Bean("backupExchange")
	public FanoutExchange backupExchange() {
		return new FanoutExchange(BACKUP_EXCHANGE_NAME);
	}
	@Bean("warningQueue")
	public Queue warningQueue(){
		return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
	}
	@Bean("backupQueue")
	public Queue backupQueue(){
		return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
	}
	
	@Bean
	@DependsOn({"confirmExchange","confirmQueue"})
	public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue queue,@Qualifier("confirmExchange")DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTE_KEY);
	}
	
	@Bean
	@DependsOn({"backupQueue","backupExchange"})
	public Binding backqueueBindingExchange(@Qualifier("backupQueue") Queue queue,@Qualifier("backupExchange")FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	@Bean
	@DependsOn({"warningQueue","backupExchange"})
	public Binding warnqueueBindingExchange(@Qualifier("warningQueue") Queue queue,@Qualifier("backupExchange")FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
}
