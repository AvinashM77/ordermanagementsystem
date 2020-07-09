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

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save(Order order) throws OrderBaseException {
		order.setOrderId(UUID.randomUUID().toString());
		int update;
		try {
			update = jdbcTemplate.update(
					"insert into Orders (ORDERID, CUSTOMER_NAME, ORDER_DATE, ADDRESS_ID, AMOUNT) values(?,?,?,?,?)",
					order.getOrderId(), order.getCustomerName(), new Date(), order.getShippingAddressId(),
					order.getAmount());
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
		return update >= 1 ? order.getOrderId() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderResponse get(String orderId) throws OrderBaseException {
		try {
			return jdbcTemplate.queryForObject(
					"select ORDERID, CUSTOMER_NAME, ORDER_DATE, o.ADDRESS_ID as ADDRESS_ID, STREET, CITY, STATE, ZIP, COUNTRY, AMOUNT "
							+ "from Orders o inner join Address a where o.ORDERID = ? and a.ADDRESS_ID = o.ADDRESS_ID",
					new Object[] { orderId },
					(rs, rowNum) -> new OrderResponse(rs.getString("ORDERID"), rs.getString("CUSTOMER_NAME"),
							rs.getDate("ORDER_DATE"),
							new Address(rs.getString("ADDRESS_ID"), rs.getString("STREET"), rs.getString("CITY"),
									rs.getString("STATE"), rs.getString("ZIP"), rs.getString("COUNTRY")),
							rs.getDouble("AMOUNT")));
		} catch (EmptyResultDataAccessException e) {
			throw new OrderBaseException("No Order Found", e);
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
			return jdbcTemplate.query("select * from Orders where CUSTOMER_NAME = ?", new Object[] { customerName },
					(rs, rowNum) -> new Order(rs.getString("ORDERID"), rs.getString("CUSTOMER_NAME"),
							rs.getDate("ORDER_DATE"), rs.getString("ADDRESS_ID"), rs.getDouble("AMOUNT")));
		} catch (EmptyResultDataAccessException e) {
			throw new OrderBaseException("No Orders Found", e);
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
			return jdbcTemplate.query("select * from Orders where ORDER_DATE between ? and ? ",
					new Object[] { startDate, endDate },
					(rs, rowNum) -> new Order(rs.getString("ORDERID"), rs.getString("CUSTOMER_NAME"),
							rs.getDate("ORDER_DATE"), rs.getString("ADDRESS_ID"), rs.getDouble("AMOUNT")));
		} catch (EmptyResultDataAccessException e) {
			throw new OrderBaseException("No Orders Found", e);
		} catch (DataAccessException e) {
			throw new OrderBaseException(e.getMessage(), e);
		}
	}

}
