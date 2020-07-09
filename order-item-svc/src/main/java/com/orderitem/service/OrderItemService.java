package com.orderitem.service;

import java.util.List;

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

import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.model.BaseResponse;
import com.orderitem.model.OrderItem;
import com.orderitem.model.OrderItemRequest;
import com.orderitem.model.OrderItemResponse;

@Component
@Path("/orderitem")
public class OrderItemService {

	private static final Logger log = LogManager.getLogger(OrderItemService.class);

	@Autowired
	IOrderItemDAO iOrderDAO;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResponse createOrderItem(@Valid OrderItemRequest orderRequest) {
		log.info(" OrderItemService/createOrderItem ");

		boolean flag = iOrderDAO.save(orderRequest.getOrderItems(), orderRequest.getOrderId());
		if (flag) {
			return new BaseResponse(null, "SUCCESS", "OrderItems Added Successfully");
		} else {
			return new BaseResponse(null, "FAILURE", "Could not add OrderItems");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{orderId}")
	public OrderItemResponse getOrderItems(@PathParam("orderId") String orderId) {
		log.info(" OrderItemService/getOrderInfo ");
		List<OrderItem> orderItems = iOrderDAO.getOrderItemsByOrderId(orderId);
		return new OrderItemResponse(orderItems, orderItems != null ? orderItems.size() : 0);
	}

}