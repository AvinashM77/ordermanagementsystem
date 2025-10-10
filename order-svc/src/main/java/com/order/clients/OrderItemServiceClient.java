/**
 * 
 */
package com.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.order.model.BaseResponse;
import com.order.model.OrderItemRequest;
import com.order.model.OrderItemResponse;

/**
 * @author amake
 *
 */
@FeignClient(value = "orderItemClient", url = "${api-client.host}"+"${api-client.order-items-endpoint}")
public interface OrderItemServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "", produces = "application/json")
	BaseResponse addOrderItems(@RequestBody OrderItemRequest orderItemRequest);

	@RequestMapping(method = RequestMethod.GET, value = "/{orderId}", produces = "application/json")
	OrderItemResponse getOrderItems(@PathVariable("orderId") String orderId);

}
