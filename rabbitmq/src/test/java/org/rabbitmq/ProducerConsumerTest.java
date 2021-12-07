package org.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.rabbitmq.one.Consumer;
import org.rabbitmq.one.Producer;

public class ProducerConsumerTest {
	@Test
	public void testSend() throws IOException, TimeoutException {
		Producer producer = new Producer();
		producer.send("hello", "192.168.137.120", "admin", "111111", "HELLO");
	}
	@Test
	public void testReceive() throws IOException, TimeoutException {
		Consumer consumer = new Consumer();
		consumer.receive("hello", "192.168.137.120", "admin", "111111");
	}
}
