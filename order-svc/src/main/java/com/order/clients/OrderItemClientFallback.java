/**
 * 
 */
package com.order.clients;

import org.springframework.stereotype.Component;

import com.order.model.BaseResponse;
import com.order.model.OrderItemRequest;
import com.order.model.OrderItemResponse;
import com.order.test.utils.TestDataUtils;

/**
 * @author Avinash
 *
 */
@Component
public class OrderItemClientFallback implements OrderItemServiceClient {

	@Override
	public BaseResponse addOrderItems(OrderItemRequest orderItemRequest) {
		return new BaseResponse(null, "SUCCESS", "OrderItems Added Successfully");
	}

	@Override
	public OrderItemResponse getOrderItems(String orderId) {
		return new OrderItemResponse(TestDataUtils.prepareRequest().getOrderItem(), 2);
	}

}
