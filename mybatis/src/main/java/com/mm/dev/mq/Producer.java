package com.mm.dev.mq;
import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer implements CommandLineRunner {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;
	
	@Autowired
	private Topic topic;
	
	
	@Override
	public void run(String... args) throws Exception {
		send("Sample message");
		System.out.println("Message was sent to the Queue");
	}

	public void send(String msg) {
		//this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
	}

}