package com.mm.dev.mq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class MqController {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;
	
	@Autowired
	private Consumer consumer;
	
/*	private Topic topic;*/
	
	@RequestMapping(value = "/produce/{value}")
	public Object produce(@PathVariable("value") String msg) {
		//this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		//this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		this.jmsMessagingTemplate.convertAndSend( new ActiveMQQueue("sample.queue"), msg);
		//consumer.receiveQueue(text)
		return msg;
	}
}
