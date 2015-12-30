package com.mm.dev.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mm.dev.controller.Message;

@RestController
@RequestMapping("/mongo")
public class MongoController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private Environment env;
	
/*	private Topic topic;*/
	
	@RequestMapping(value = "/produce/{value}")
	public Message produce(@PathVariable("value") String msg) {
		//this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		//this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		ObjectNode node = new ObjectMapper().createObjectNode();
		//consumer.receiveQueue(text)
		Customer customer = new Customer("Gary", "Guo");
		
		Message message = new Message();
	
		try {
			//mongoTemplate.save(customer);
			//mongoTemplate.find
			//customerRepository.save(customer);
			List<Customer> list = customerRepository.findAll();
			node.putPOJO("data", list);
			//message.setMsg(1000, "");
			message.setMsg(1000, env.getProperty("1000"), list);
			System.out.println(111);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
}
