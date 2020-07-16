package com.orderitem.service;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.model.BaseResponse;
import com.orderitem.model.OrderItem;
import com.orderitem.model.OrderItemRequest;
import com.orderitem.model.OrderItemResponse;

@RestController
public class OrderItemController {

	private static final Logger log = LogManager.getLogger(OrderItemController.class);

	@Autowired
	IOrderItemDAO iOrderDAO;

	@PostMapping(path = "/orderitem", produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse createOrderItem(@Valid @RequestBody OrderItemRequest orderRequest) {
		log.info(" OrderItemService/createOrderItem ");

		boolean flag = iOrderDAO.save(orderRequest.getOrderItems(), orderRequest.getOrderId());
		if (flag) {
			return new BaseResponse(null, "SUCCESS", "OrderItems Added Successfully");
		} else {
			return new BaseResponse(null, "FAILURE", "Could not add OrderItems");
		}
	}

	@GetMapping(path = "/orderitem/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderItemResponse getOrderItems(@PathVariable("orderId") String orderId) {
		log.info(" OrderItemService/getOrderInfo ");
		List<OrderItem> orderItems = iOrderDAO.getOrderItemsByOrderId(orderId);
		return new OrderItemResponse(orderItems, orderItems != null ? orderItems.size() : 0);
	}

}