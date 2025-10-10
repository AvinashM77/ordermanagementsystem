package com.orderitem.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.orderitem.model.OrderItem;

@Component
public class OrderItemDAOImpl implements IOrderItemDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_ORDER_ITEMS =
        "INSERT INTO OrderItems (ORDERITEMID, ORDERID, PRODUCT_CODE, PRODUCT_NAME, QUANTITY) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_ITEM_BY_ID =
        "SELECT * FROM OrderItems WHERE ORDERITEMID = ?";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID =
        "SELECT * FROM OrderItems WHERE ORDERID = ?";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(List<OrderItem> orderItems, String orderId) {
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                INSERT_ORDER_ITEMS,
                orderItems,
                1000,
                (PreparedStatement ps, OrderItem item) -> {
                    ps.setString(1, UUID.randomUUID().toString());
                    ps.setString(2, orderId);
                    ps.setString(3, item.getProductCode());
                    ps.setString(4, item.getProductName());
                    ps.setInt(5, item.getQuantity());
                });
        return updateCounts.length >= 1;
    }

    @Override
    public OrderItem get(String orderItemId) {
        return jdbcTemplate.query(
                SELECT_ORDER_ITEM_BY_ID,
                ps -> ps.setString(1, orderItemId),
                (rs, rowNum) -> new OrderItem(
                        rs.getString("ORDERITEMID"),
                        rs.getString("ORDERID"),
                        rs.getString("PRODUCT_CODE"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("QUANTITY")))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return jdbcTemplate.query(
                SELECT_ORDER_ITEMS_BY_ORDER_ID,
                ps -> ps.setString(1, orderId),
                (rs, rowNum) -> new OrderItem(
                        rs.getString("ORDERITEMID"),
                        rs.getString("ORDERID"),
                        rs.getString("PRODUCT_CODE"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("QUANTITY")));
    }
}