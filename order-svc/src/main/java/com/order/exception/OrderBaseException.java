package com.order.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.order.model.BaseResponse;

@Provider
public class OrderBaseException extends Exception implements ExceptionMapper<OrderBaseException> {

	private static final long serialVersionUID = 1L;

	public OrderBaseException() {
		super("Generic Error");
	}

	public OrderBaseException(String message) {
		super(message);
	}

	public OrderBaseException(String message, Throwable e) {
		super(message, e);
	}

	@Override
	public Response toResponse(OrderBaseException exception) {
		return Response.status(500).entity(new BaseResponse(null, "FAILURE", exception.getMessage()))
				.type("application/json").build();
	}

}
