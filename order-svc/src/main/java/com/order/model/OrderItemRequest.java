/**
 * 
 */
package com.order.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author amake
 *
 */
public class OrderItemRequest {

	@NotEmpty
	@NotNull
	private String orderId;

	@Size(min = 1)
	@NotNull(message = "OrderItems cannot be empty")
	private List<OrderItem> orderItems;
	
	/**
	 * @param orderId
	 * @param orderItems
	 */
	public OrderItemRequest(@NotEmpty @NotNull String orderId,
			@Size(min = 1) @NotNull(message = "OrderItems cannot be empty") List<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.orderItems = orderItems;
	}

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
