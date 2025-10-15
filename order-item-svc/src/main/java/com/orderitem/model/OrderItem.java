/**
 * 
 */
package com.orderitem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author amake
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderItem {

	private String orderItemId;
	private String orderId;
	private String productCode;
	private String productName;
	private int quantity;

}