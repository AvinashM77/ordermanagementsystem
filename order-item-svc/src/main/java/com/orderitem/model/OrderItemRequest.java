/**
 * 
 */
package com.orderitem.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author amake
 *
 */
@Setter
@Getter
public class OrderItemRequest {

	@NotEmpty
	@NotNull(message = "OrderId cannot be empty")
	private String orderId;

	@Size(min = 1)
	@NotNull(message = "OrderItems cannot be empty")
	private List<OrderItem> orderItems;

}
