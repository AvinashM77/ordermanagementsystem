/**
 * 
 */
package com.order.model;

import java.util.Date;

/**
 * @author amake
 *
 */
public class Order {

	private String orderId;
	private String customerName;
	private Date orderDate;
	private String shippingAddressId;
	private double amount;

	/**
	 * 
	 */
	public Order() {
		super();
	}

	/**
	 * @param customerName
	 * @param orderDate
	 * @param shippingAddressId
	 * @param amount
	 */
	public Order(String customerName, String shippingAddressId, double amount) {
		super();
		this.customerName = customerName;
		this.shippingAddressId = shippingAddressId;
		this.amount = amount;
	}

	/**
	 * @param orderId
	 * @param customerName
	 * @param orderDate
	 * @param shippingAddress
	 * @param orderItemIds
	 * @param amount
	 */
	public Order(String orderId, String customerName, Date orderDate, String shippingAddressId, double amount) {
		this.orderId = orderId;
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.shippingAddressId = shippingAddressId;
		this.amount = amount;
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

	/**
	 * @return the shippingAddressId
	 */
	public String getShippingAddressId() {
		return shippingAddressId;
	}

	/**
	 * @param shippingAddressId the shippingAddressId to set
	 */
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

}