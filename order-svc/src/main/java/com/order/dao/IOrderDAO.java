package com.order.dao;

import java.util.Date;
import java.util.List;

import com.order.exception.OrderBaseException;
import com.order.model.Order;
import com.order.model.OrderResponse;

/**
 * @author amake
 *
 */
public interface IOrderDAO {

	/**
	 * creates the order.
	 * 
	 * @param order
	 * @return
	 */
	String save(Order order) throws OrderBaseException;

	/**
	 * gets the order by Id.
	 * 
	 * @param orderId
	 * @return
	 */
	OrderResponse get(String orderId) throws OrderBaseException;

	/**
	 * get Orders by customerName.
	 * 
	 * @param customerName
	 * @return
	 */
	List<Order> getOrdersByCustomerName(String customerName) throws OrderBaseException;

	/**
	 * get Orders by date range.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Order> getOrdersByDateRange(Date startDate, Date endDate) throws OrderBaseException;

}
