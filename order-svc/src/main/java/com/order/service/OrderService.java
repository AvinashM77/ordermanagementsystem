package com.order.service;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.order.clients.OrderItemServiceClient;
import com.order.dao.IAddressDAO;
import com.order.dao.IOrderDAO;
import com.order.exception.OrderBaseException;
import com.order.model.BaseResponse;
import com.order.model.Order;
import com.order.model.OrderItemRequest;
import com.order.model.OrderItemResponse;
import com.order.model.OrderRequest;
import com.order.model.OrderResponse;

@Component
@Path("/order")
public class OrderService {

	private static final Logger log = LogManager.getLogger(OrderService.class);

	@Autowired
	IOrderDAO iOrderDAO;

	@Autowired
	IAddressDAO iAddressDAO;

	@Autowired
	OrderItemServiceClient orderItemServiceClient;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse createOrder(@Valid OrderRequest orderRequest) throws OrderBaseException {
		log.info(" OrderService/createOrder ");

		String addressId = iAddressDAO.save(orderRequest.getShippingAddress());

		if (addressId == null) {
			throw new OrderBaseException("Order Creation Failed while updating address");
		}

		String orderId = iOrderDAO.save(new Order(orderRequest.getCustomerName(), addressId, orderRequest.getAmount()));

		try {
			BaseResponse response = orderItemServiceClient
					.addOrderItems(new OrderItemRequest(orderId, orderRequest.getOrderItem()));
			if (response != null && "SUCCESS".equals(response.getResult())) {
				log.info(" OrderService/orderItems :: {}", response.getMessage());
			}
		} catch (Exception e) {
			throw new OrderBaseException(e.getMessage());
		}

		return new BaseResponse(orderId, "SUCCESS", "Order Created Successfully");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{orderId}")
	public OrderResponse getOrderInfo(@PathParam("orderId") String orderId) throws OrderBaseException {
		log.info(" OrderService/getOrderInfo ");
		OrderItemResponse orderItemResponse = null;
		try {
			orderItemResponse = orderItemServiceClient.getOrderItems(orderId);
		} catch (Exception e) {
			throw new OrderBaseException(e.getMessage());
		}

		if (orderItemResponse == null) {
			throw new OrderBaseException("No OrderItems Found");
		}

		OrderResponse orderResponse = iOrderDAO.get(orderId);

		if (orderResponse == null) {
			throw new OrderBaseException("No Order Found");
		}

		orderResponse.setOrderItems(orderItemResponse.getOrderItems());
		return orderResponse;
	}

}