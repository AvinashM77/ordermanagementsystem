package com.order.test;


import com.order.dao.AddressDAOImpl;
import com.order.dao.IAddressDAO;
import com.order.dao.IOrderDAO;
import com.order.dao.OrderDAOImpl;
import com.order.exception.OrderBaseException;
import com.order.model.Order;
import com.order.model.OrderRequest;
import com.order.model.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.order.test.utils.TestDataUtils.prepareRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderDAOTest {

    JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

    private IAddressDAO iAddressDAO;
    private IOrderDAO iOrderDAO;

    @BeforeEach
    void setUp() {
        iAddressDAO = new AddressDAOImpl(mockJdbcTemplate);
        iOrderDAO = new OrderDAOImpl(mockJdbcTemplate);
    }

    private static String addressId;
    private static String orderId;

    @SuppressWarnings("unchecked")
    @Test
    @org.junit.jupiter.api.Order(1)
    public void createAddressTest() throws IOException {
        when(mockJdbcTemplate.query(anyString(), any(PreparedStatementSetter.class) ,any(RowMapper.class)))
                .thenReturn(List.of());
        when(mockJdbcTemplate.update(anyString(), any(PreparedStatementSetter.class))).thenReturn(1);
        addressId = iAddressDAO.save(Objects.requireNonNull(prepareRequest()).getShippingAddress());
        assertNotNull(addressId);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void createOrderTest() throws OrderBaseException, IOException {
        when(mockJdbcTemplate.update(anyString(), any(PreparedStatementSetter.class))).thenReturn(1);
        OrderRequest orderRequest = prepareRequest();
        orderId = iOrderDAO.save(new Order(orderRequest.getCustomerName(), addressId, orderRequest.getAmount()));
        assertNotNull(orderId);
    }

    @SuppressWarnings("unchecked")
    @Test
    @org.junit.jupiter.api.Order(3)
    public void getOrderTest() throws OrderBaseException {
        when(mockJdbcTemplate.query(anyString(), any(PreparedStatementSetter.class), any(RowMapper.class)))
                .thenReturn(List.of(new OrderResponse(orderId, "Test User", null, null, 100.0)));
        OrderResponse orderResponse = iOrderDAO.get(orderId);
        assertNotNull(orderResponse);
    }

}