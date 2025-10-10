package com.orderitem.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.orderitem.test.config.DBConfig;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.google.gson.Gson;
import com.orderitem.OrderItemApp;
import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.model.OrderItem;
import com.orderitem.model.OrderItemRequest;
import com.orderitem.test.config.TestAppConfig;

@SpringBootTest(classes = OrderItemApp.class)
@ContextConfiguration(classes = DBConfig.class)
class OrderItemDAOTest {

    private static final Logger log = LogManager.getLogger(OrderItemDAOTest.class);

    @Autowired
    private IOrderItemDAO iOrderItemDAO;

    @Test
    void createOrderItem() {
        log.info(" OrderItemServiceTest/createOrderItem ");
        OrderItemRequest orderRequest = prepareRequest();
        boolean flag = iOrderItemDAO.save(orderRequest.getOrderItems(), orderRequest.getOrderId());
        assertTrue(flag, "Order items should be saved successfully");
    }

    @Test
    void getOrderItems() {
        log.info(" OrderItemServiceTest/getOrderInfo ");
        String orderId = "f809a7ea-9be2-41ca-b112-0adf2cba5e04";
        List<OrderItem> orderItems = iOrderItemDAO.getOrderItemsByOrderId(orderId);
        assertEquals(2, orderItems.size(), "Should find 2 order items");
    }

    private OrderItemRequest prepareRequest() {
        try (InputStream stream = OrderItemDAOTest.class.getClassLoader()
                .getResourceAsStream("orderItemRequest.json")) {
            StringWriter writer = new StringWriter();
            Assertions.assertNotNull(stream);
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            String json = writer.toString();
            return new Gson().fromJson(json, OrderItemRequest.class);
        } catch (IOException e) {
            log.error("Failed to load test data", e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}