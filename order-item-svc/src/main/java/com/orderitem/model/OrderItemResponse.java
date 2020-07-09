/**
 * 
 */
package com.orderitem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author amake
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse extends BaseResponse {

	private List<OrderItem> orderItems;
	private long count;

	/**
	 * @param orderItems
	 * @param count
	 */
	public OrderItemResponse(List<OrderItem> orderItems, long count) {
		super();
		this.orderItems = orderItems;
		this.setCount(count);
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

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

}
