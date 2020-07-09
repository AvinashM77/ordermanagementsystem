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
	 * @param order
	 * @return
	 */
	boolean save(List<OrderItem> orderItems, String orderId);

	/**
	 * gets the order by Id.
	 * 
	 * @param orderId
	 * @return
	 */
	OrderItem get(String orderItemId);

	/**
	 * get Orders by customerName.
	 * 
	 * @param customerName
	 * @return
	 */
	List<OrderItem> getOrderItemsByOrderId(String orderId);

}
