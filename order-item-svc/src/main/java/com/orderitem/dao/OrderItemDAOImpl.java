/**
 * 
 */
package com.orderitem.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.orderitem.model.OrderItem;

/**
 * @author amake
 *
 */
@Component
public class OrderItemDAOImpl implements IOrderItemDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean save(List<OrderItem> orderItems, String orderId) {
		int[][] updateCounts = jdbcTemplate.batchUpdate(
				"insert into OrderItems (ORDERITEMID, ORDERID, PRODUCT_CODE, PRODUCT_NAME, QUANTITY) values(?,?,?,?,?)",
				orderItems, 1000, new ParameterizedPreparedStatementSetter<OrderItem>() {
					public void setValues(PreparedStatement ps, OrderItem argument) throws SQLException {
						ps.setString(1, UUID.randomUUID().toString());
						ps.setString(2, orderId);
						ps.setString(3, argument.getProductCode());
						ps.setString(4, argument.getProductName());
						ps.setInt(5, argument.getQuantity());
					}
				});
		return updateCounts != null && updateCounts.length >= 1 ? true : false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderItem get(String orderItemId) {
		return jdbcTemplate.queryForObject("select * from OrderItems where ORDERITEMID = ?",
				new Object[] { orderItemId },
				(rs, rowNum) -> new OrderItem(rs.getString("ORDERITEMID"), rs.getString("ORDERID"),
						rs.getString("PRODUCT_CODE"), rs.getString("PRODUCT_NAME"), rs.getInt("QUANTITY")));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OrderItem> getOrderItemsByOrderId(String orderId) {
		return jdbcTemplate.query("select * from OrderItems where ORDERID = ?", new Object[] { orderId },
				(rs, rowNum) -> new OrderItem(rs.getString("ORDERITEMID"), rs.getString("ORDERID"),
						rs.getString("PRODUCT_CODE"), rs.getString("PRODUCT_NAME"), rs.getInt("QUANTITY")));
	}

}