package org.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.rabbitmq.one.Producer;
import org.rabbitmq.two.ReceiveWork01;

public class ThreadTest {
	
	@Test
	public void testperiod() {
		
		int s1 =1,e1=100;
		int s2=3,e2=300;
		
		System.out.println(doPeriod(1,100,3,300));
		System.out.println(doPeriod(3,300,1,100));
		
	}
	public int doPeriod(int s1,int e1,int s2,int e2) {
		int result=0;
		int end=e2;
		if(s2<=s1 && s1<=e2) {
			end=e1<e2?e1:e2;
			result =end-s1; 
		}else if(s1<=s2 && s2<=e1) {
			end=e2<e1?e2:e1;
			result= end-s2;
		}
		return result;
	}
	@Test
	public void test() throws IOException, TimeoutException {
		
		new Thread() {
		    @Override
		    public void run() {
		    	ReceiveWork01 receive = new ReceiveWork01();
		    	try {
					receive.receive("R1");
				} catch (IOException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();
		
		new Thread() {
		    @Override
		    public void run() {
		    	ReceiveWork01 receive = new ReceiveWork01();
		    	try {
					receive.receive("R2");
				} catch (IOException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();		
		Producer producer = new Producer();
		producer.sendDefault("MESSAGE_01");
		producer.sendDefault("MESSAGE_02");

	}
	/*public static void main(String[] args) throws IOException, TimeoutException {
		new Thread() {
		    @Override
		    public void run() {
		    	ReceiveWork01 receive = new ReceiveWork01();
		    	try {
					receive.receive("R1");
				} catch (IOException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();
		
		new Thread() {
		    @Override
		    public void run() {
		    	ReceiveWork01 receive = new ReceiveWork01();
		    	try {
					receive.receive("R2");
				} catch (IOException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();		
		Producer producer = new Producer();
		for(int i=0;i<10;i++) {
			producer.sendDefault("MESSAGE_0"+i);
		}
	}*/
}
