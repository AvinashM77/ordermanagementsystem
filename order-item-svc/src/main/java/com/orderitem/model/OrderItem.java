/**
 * 
 */
package com.orderitem.model;

/**
 * @author amake
 *
 */
public class OrderItem {

	private String orderItemId;
	private String orderId;
	private String productCode;
	private String productName;
	private int quantity;

	/**
	 * 
	 */
	public OrderItem() {

	}

	/**
	 * @param orderItemId
	 * @param orderId
	 * @param productCode
	 * @param productName
	 * @param quantity
	 */
	public OrderItem(String orderItemId, String orderId, String productCode, String productName, int quantity) {
		this.orderItemId = orderItemId;
		this.orderId = orderId;
		this.productCode = productCode;
		this.productName = productName;
		this.quantity = quantity;
	}

	/**
	 * @return the orderItemId
	 */
	public String getOrderItemId() {
		return orderItemId;
	}

	/**
	 * @param orderItemId the orderItemId to set
	 */
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

}