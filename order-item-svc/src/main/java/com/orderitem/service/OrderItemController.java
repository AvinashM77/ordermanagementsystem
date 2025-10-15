package com.orderitem.service;

import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.model.BaseResponse;
import com.orderitem.model.OrderItem;
import com.orderitem.model.OrderItemRequest;
import com.orderitem.model.OrderItemResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderItemController {

	private final IOrderItemDAO iOrderDAO;

    @PostMapping(path = "/orderitem", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> createOrderItem(@Valid @RequestBody OrderItemRequest orderRequest) {
		log.info(" OrderItemService/createOrderItem ");

		boolean flag = iOrderDAO.save(orderRequest.getOrderItems(), orderRequest.getOrderId());
		if (flag) {
            return ResponseEntity.of(Optional.of(new BaseResponse(null, "SUCCESS", "OrderItems Added Successfully")));
		} else {
            return ResponseEntity.internalServerError().body(new BaseResponse(null, "FAILURE", "Could not add OrderItems"));
		}
	}

	@GetMapping(path = "/orderitem/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderItemResponse getOrderItems(@PathVariable("orderId") String orderId) {
		log.info(" OrderItemService/getOrderInfo ");
		List<OrderItem> orderItems = iOrderDAO.getOrderItemsByOrderId(orderId);
		return new OrderItemResponse(orderItems, orderItems != null ? orderItems.size() : 0);
	}

}