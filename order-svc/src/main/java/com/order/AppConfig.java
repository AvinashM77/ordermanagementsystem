package com.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.clients.exception.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class AppConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

}