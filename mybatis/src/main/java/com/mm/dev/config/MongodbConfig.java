package com.mm.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@PropertySource(value = {"classpath:/mongodb.properties"})
public class MongodbConfig {
	
    @Bean
    public MongoTemplate redisTemplate(MongoDbFactory factory) {
    	return new MongoTemplate(factory);
    }
}