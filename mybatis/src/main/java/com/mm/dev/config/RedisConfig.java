package com.mm.dev.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.SessionRepositoryFilter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@PropertySource(value = {"classpath:/redis.properties"})
@EnableRedisHttpSession
@EnableCaching
public class RedisConfig {
	
	 private Integer maxInactiveIntervalInSeconds = 100;

	 @Bean
	 public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

	 }

	 	@Bean
	    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
	        return new RedisCacheManager(redisTemplate);
	    }

	    @Bean
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
	        StringRedisTemplate template = new StringRedisTemplate(factory);
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	        template.afterPropertiesSet();
	        return template;
	    }
	    
	    
	    @Bean
	    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory connectionFactory) {
	    	RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setConnectionFactory(connectionFactory);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	        template.afterPropertiesSet();
	        
	        return template;
	    }
	   
	   
	    @Bean
	    public RedisTemplate<String,ExpiringSession> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
	        RedisTemplate<String, ExpiringSession> template = new RedisTemplate<String, ExpiringSession>();
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setHashKeySerializer(new StringRedisSerializer());
	        template.setConnectionFactory(connectionFactory);
	        return template;
	    }

	    @Bean
	    public RedisOperationsSessionRepository sessionRepository(RedisTemplate<String, ExpiringSession> sessionRedisTemplate) {
	        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
	        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
	        return sessionRepository;
	    }

	    @Bean
	    public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(SessionRepository<S> sessionRepository) {
	        SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
	        return sessionRepositoryFilter;
	    }
	
}