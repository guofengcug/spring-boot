package com.mm.dev;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mm.dev.interceptors.UserSecurityInterceptor;

/**
 * Created by Lipengfei on 2015/6/24.
 */
@SpringBootApplication
@PropertySource(value = {"classpath:/application.properties", "classpath:/datasource.properties"})
//@EnableWebMvc
@EnableAutoConfiguration
@Order(HIGHEST_PRECEDENCE)
public class DevApplication extends WebMvcConfigurerAdapter {

	@Autowired
	private UserSecurityInterceptor userSecurityInterceptor;
	
    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
    }
    
   /** 
     * 配置拦截器 
     * @author guofeng 
     * @param registry 
     */
    public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(userSecurityInterceptor).addPathPatterns("/**").
        	excludePathPatterns("/login/**").
        	excludePathPatterns("/debug/**").
        	excludePathPatterns("/api-docs/**").
        	excludePathPatterns("/mq/**").
        	excludePathPatterns("/mongo/**").
        	excludePathPatterns("/favicon.ico");  
    } 

}
