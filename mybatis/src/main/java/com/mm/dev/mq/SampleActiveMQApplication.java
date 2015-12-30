package com.mm.dev.mq;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class SampleActiveMQApplication {

	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}
	
	@Bean
	public Topic topic() {
		return new ActiveMQTopic("sample.topic");
	}

}
