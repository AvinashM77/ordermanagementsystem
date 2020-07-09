package com.order;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.clients.exception.CustomErrorDecoder;
import com.order.exception.OrderBaseException;
import com.order.service.OrderService;

import feign.codec.ErrorDecoder;

@Configuration
public class AppConfig extends ResourceConfig {

	public AppConfig() {
		register(OrderService.class);
		register(OrderBaseException.class);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

}