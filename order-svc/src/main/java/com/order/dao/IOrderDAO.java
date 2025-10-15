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
	 * @param order order
	 * @return orderId
     * @throws OrderBaseException on failure
	 */
	String save(Order order) throws OrderBaseException;

	/**
	 * gets the order by Id.
	 * 
	 * @param orderId orderId
	 * @return OrderResponse
     * @throws OrderBaseException on failure
	 */
	OrderResponse get(String orderId) throws OrderBaseException;

	/**
	 * get Orders by customerName.
	 * 
	 * @param customerName customerName
	 * @return list of orders
     * @throws OrderBaseException on failure
	 */
	List<Order> getOrdersByCustomerName(String customerName) throws OrderBaseException;

	/**
	 * get Orders by date range.
	 * 
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return list of orders
     * @throws OrderBaseException on failure
	 */
	List<Order> getOrdersByDateRange(Date startDate, Date endDate) throws OrderBaseException;

}
