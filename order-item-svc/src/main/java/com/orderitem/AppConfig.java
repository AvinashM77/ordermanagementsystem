package com.orderitem;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.orderitem.service.OrderItemService;

@Configuration
public class AppConfig extends ResourceConfig {

	public AppConfig() {
		register(OrderItemService.class);
	}

}