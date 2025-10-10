package com.orderitem.dao;

import com.orderitem.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class OrderItemDAOImpl implements IOrderItemDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_ORDER_ITEMS =
        "INSERT INTO OrderItems (ORDERITEMID, ORDERID, PRODUCT_CODE, PRODUCT_NAME, QUANTITY) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID =
        "SELECT * FROM OrderItems WHERE ORDERID = ?";

    @Override
    public boolean save(List<OrderItem> orderItems, String orderId) {
        int[][] updateCounts;
        try {
            updateCounts = jdbcTemplate.batchUpdate(
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
        } catch (DataAccessException e) {
            log.error("Error inserting order items", e);
            return false;
        }
        return updateCounts.length >= 1;
    }



    @Override
    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return jdbcTemplate.query(
                SELECT_ORDER_ITEMS_BY_ORDER_ID,
                ps -> ps.setString(1, orderId),
                (rs, _) -> new OrderItem(
                        rs.getString("ORDERITEMID"),
                        rs.getString("ORDERID"),
                        rs.getString("PRODUCT_CODE"),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("QUANTITY")));
    }
}