package com.order.service;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger log = LogManager.getLogger(OrderController.class);

	@Autowired
	IOrderDAO iOrderDAO;

	@Autowired
	IAddressDAO iAddressDAO;

	@Autowired
	OrderItemServiceClient orderItemServiceClient;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) throws OrderBaseException {
		log.info(" OrderService/createOrder ");

		String addressId = iAddressDAO.save(orderRequest.getShippingAddress());

		if (addressId == null) {
			throw new OrderBaseException("Order Creation Failed while updating address");
		}

		String orderId = iOrderDAO.save(new Order(orderRequest.getCustomerName(), addressId, orderRequest.getAmount()));

		try {
			BaseResponse response =
                    orderItemServiceClient.addOrderItems(new OrderItemRequest(orderId, orderRequest.getOrderItem()));
			if (response != null && "SUCCESS".equals(response.getResult())) {
				log.info(" OrderService/orderItems :: {}", response.getMessage());
			}
		} catch (Exception e) {
			throw new OrderBaseException(e.getMessage());
		}

		return new BaseResponse(orderId, "SUCCESS", "Order Created Successfully");
	}

	@GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderResponse getOrderInfo(@PathVariable("orderId") String orderId) throws OrderBaseException {
		log.info(" OrderService/getOrderInfo ");
		OrderItemResponse orderItemResponse;
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