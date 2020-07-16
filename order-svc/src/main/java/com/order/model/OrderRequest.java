/**
 * 
 */
package com.order.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author amake
 *
 */
public class OrderRequest {

	@NotEmpty(message = "Name cannot be empty")
	@NotNull(message = "Name cannot be null")
	private String customerName;
	private Date orderDate;
	@NotNull(message = "Address cannot be empty")
	private Address shippingAddress;
	@Size(min = 1)
	@NotNull(message = "OrderItems cannot be empty")
	private List<OrderItem> orderItem;
	@Positive(message = "Amount cannot be empty or cannot be lessthan 1")
	private double amount;

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the orderItem
	 */
	public List<OrderItem> getOrderItem() {
		return orderItem;
	}

	/**
	 * @param orderItem the orderItem to set
	 */
	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

}
