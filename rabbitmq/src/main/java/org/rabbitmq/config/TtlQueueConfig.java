package org.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TtlQueueConfig {
	private static final String X_EXCHANGE = "X";
	private static final String Y_DEAD_LETTER_EXCHANGE = "Y";

	private static final String QUEUE_A = "A";
	private static final String QUEUE_B = "B";

	private static final String DEAD_LETER_QUEUE = "QD";

	@Bean("xExchange")
	public DirectExchange xExchange() {
		return new DirectExchange(X_EXCHANGE);
	}

	@Bean("yExchange")
	public DirectExchange yExchange() {
		return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
	}

	@Bean("queueA")
	public Queue queueA() {
		Map<String, Object> arguments = new HashMap<>(3);
		arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
		arguments.put("x-dead-letter-routing-key", "YD");
		arguments.put("x-message-ttl", 10 * 1000);
		return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
	}
	@Bean("queueB")
	public Queue queueB() {
		Map<String, Object> arguments = new HashMap<>(3);
		arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
		arguments.put("x-dead-letter-routing-key", "YD");
		arguments.put("x-message-ttl", 40 * 1000);
		return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
	}	
	@Bean("queueD")
	public Queue queueD() {
		return QueueBuilder.durable(DEAD_LETER_QUEUE).build();
	}
	@Bean
	public Binding QueueABingX(@Qualifier(value="queueA") Queue queueA,@Qualifier("xExchange") DirectExchange xExchange) {
		return  BindingBuilder.bind(queueA).to(xExchange).with("XA");
	}
	@Bean
	public Binding QueueBBingX(@Qualifier(value="queueB") Queue queueB,@Qualifier("xExchange") DirectExchange xExchange) {
		return  BindingBuilder.bind(queueB).to(xExchange).with("XB");
	}
	@Bean
	public Binding QueueDBingX(@Qualifier(value="queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange) {
		return  BindingBuilder.bind(queueD).to(yExchange).with("YD");
	}	
	
	
}
