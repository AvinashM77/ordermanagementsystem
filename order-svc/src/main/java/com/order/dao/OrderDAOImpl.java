/**
 * 
 */
package com.order.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.order.exception.OrderBaseException;
import com.order.model.Address;
import com.order.model.Order;
import com.order.model.OrderResponse;

/**
 * @author amake
 *
 */
@Component
public class OrderDAOImpl implements IOrderDAO {

    private static final String INSERT_ORDER =
        "INSERT INTO Orders (ORDERID, CUSTOMER_NAME, ORDER_DATE, ADDRESS_ID, AMOUNT) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_WITH_ADDRESS =
        "SELECT ORDERID, CUSTOMER_NAME, ORDER_DATE, o.ADDRESS_ID as ADDRESS_ID, STREET, CITY, STATE, ZIP, COUNTRY, AMOUNT " +
        "FROM Orders o INNER JOIN Address a ON a.ADDRESS_ID = o.ADDRESS_ID WHERE o.ORDERID = ?";
    private static final String SELECT_ORDERS_BY_CUSTOMER =
        "SELECT * FROM Orders WHERE CUSTOMER_NAME = ?";
    private static final String SELECT_ORDERS_BY_DATE_RANGE =
        "SELECT * FROM Orders WHERE ORDER_DATE BETWEEN ? AND ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
	 * {@inheritDoc}
	 */
	@Override
	public String save(Order order) throws OrderBaseException {
		order.setOrderId(UUID.randomUUID().toString());
		try {
			int update = jdbcTemplate.update(
				INSERT_ORDER,
				ps -> {
					ps.setString(1, order.getOrderId());
					ps.setString(2, order.getCustomerName());
					ps.setDate(3, new java.sql.Date(new Date().getTime()));
					ps.setString(4, order.getShippingAddressId());
					ps.setDouble(5, order.getAmount());
				});
			return update >= 1 ? order.getOrderId() : null;
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderResponse get(String orderId) throws OrderBaseException {
		try {
			return jdbcTemplate.query(
				SELECT_ORDER_WITH_ADDRESS,
				ps -> ps.setString(1, orderId),
				(rs, rowNum) -> new OrderResponse(
					rs.getString("ORDERID"),
					rs.getString("CUSTOMER_NAME"),
					rs.getDate("ORDER_DATE"),
					new Address(
						rs.getString("ADDRESS_ID"),
						rs.getString("STREET"),
						rs.getString("CITY"),
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("COUNTRY")
					),
					rs.getDouble("AMOUNT")
				))
				.stream()
				.findFirst()
				.orElseThrow(() -> new OrderBaseException("No Order Found"));
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Order> getOrdersByCustomerName(String customerName) throws OrderBaseException {
		try {
			List<Order> orders = jdbcTemplate.query(
				SELECT_ORDERS_BY_CUSTOMER,
				ps -> ps.setString(1, customerName),
				(rs, rowNum) -> new Order(
					rs.getString("ORDERID"),
					rs.getString("CUSTOMER_NAME"),
					rs.getDate("ORDER_DATE"),
					rs.getString("ADDRESS_ID"),
					rs.getDouble("AMOUNT")
				));
			if (orders.isEmpty()) {
				throw new OrderBaseException("No Orders Found");
			}
			return orders;
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Order> getOrdersByDateRange(Date startDate, Date endDate) throws OrderBaseException {
		try {
			List<Order> orders = jdbcTemplate.query(
				SELECT_ORDERS_BY_DATE_RANGE,
				ps -> {
					ps.setDate(1, new java.sql.Date(startDate.getTime()));
					ps.setDate(2, new java.sql.Date(endDate.getTime()));
				},
				(rs, rowNum) -> new Order(
					rs.getString("ORDERID"),
					rs.getString("CUSTOMER_NAME"),
					rs.getDate("ORDER_DATE"),
					rs.getString("ADDRESS_ID"),
					rs.getDouble("AMOUNT")
				));
			if (orders.isEmpty()) {
				throw new OrderBaseException("No Orders Found");
			}
			return orders;
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
	}

}
