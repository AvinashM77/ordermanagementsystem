package com.orderitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.orderitem" })
public class OrderItemApp {
	public static void main(String[] args) {
		SpringApplication.run(OrderItemApp.class, args);
	}
}