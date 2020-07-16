package com.order.clients.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 400:
			return new ResponseStatusException(HttpStatus.BAD_REQUEST);
		case 404:
			return new ResponseStatusException(HttpStatus.NOT_FOUND);
		default:
			return new Exception("Generic error in OrderItems Service");
		}
	}
}