package com.orderitem.test;

import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.dao.OrderItemDAOImpl;
import com.orderitem.model.OrderItem;
import com.orderitem.model.OrderItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static com.orderitem.test.TestDataUtils.prepareRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderItemDAOTest {

    JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private IOrderItemDAO iOrderItemDAO;

    @BeforeEach
    void setUp() {
        iOrderItemDAO = new OrderItemDAOImpl(mockJdbcTemplate);
    }

    @Test
    void createOrderItem() {
        OrderItemRequest orderRequest = prepareRequest();
        when(mockJdbcTemplate.batchUpdate(anyString(), any(), anyInt(), any())).thenReturn(new int[][]{{1}, {1}});
        boolean flag = iOrderItemDAO.save(orderRequest.getOrderItems(), orderRequest.getOrderId());
        assertTrue(flag, "Order items should be saved successfully");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void getOrderItems() {
        String orderId = "f809a7ea-9be2-41ca-b112-0adf2cba5e04";
        when(mockJdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), (RowMapper) any()))
                .thenReturn(prepareRequest().getOrderItems());
        List<OrderItem> orderItems = iOrderItemDAO.getOrderItemsByOrderId(orderId);
        assertEquals(2, orderItems.size(), "Should find 2 order items");
    }


}