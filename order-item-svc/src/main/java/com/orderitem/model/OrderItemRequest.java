/**
 * 
 */
package com.orderitem.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @author amake
 *
 */
public class OrderItemRequest {

	@NotEmpty
	@NotNull(message = "OrderId cannot be empty")
	private String orderId;

	@Size(min = 1)
	@NotNull(message = "OrderItems cannot be empty")
	private List<OrderItem> orderItems;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems the orderItems to set
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
