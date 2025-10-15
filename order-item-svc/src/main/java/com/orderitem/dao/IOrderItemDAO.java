package com.orderitem.dao;

import java.util.List;

import com.orderitem.model.OrderItem;

/**
 * @author amake
 *
 */
public interface IOrderItemDAO {

	/**
	 * creates the order.
	 * 
	 * @param orderItems list of order items
     * @param orderId    orderId
	 * @return boolean
	 */
	boolean save(List<OrderItem> orderItems, String orderId);

	/**
	 * get Orders by customerName.
	 * 
	 * @param orderId orderId
	 * @return list of orders
	 */
	List<OrderItem> getOrderItemsByOrderId(String orderId);

}
