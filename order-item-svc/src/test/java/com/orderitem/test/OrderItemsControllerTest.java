package com.orderitem.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.orderitem.service.OrderItemController;
import com.orderitem.test.config.TestAppConfig;

@WebMvcTest(OrderItemController.class)
@Import(TestAppConfig.class)
class OrderItemsControllerTest {

    private static final Logger log = LogManager.getLogger(OrderItemsControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @Test
    void createOrderItemTest() throws Exception {
        String requestBody = prepareRequest();
        mvc.perform(MockMvcRequestBuilders.post("/orderitem")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
            .andDo(print());
    }

    @Test
    void getOrderItems() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orderitem/{orderId}", "f809a7ea-9be2-41ca-b112-0adf2cba5e04")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2));
    }

    private String prepareRequest() {
        try (InputStream stream = OrderItemsControllerTest.class.getClassLoader()
                .getResourceAsStream("orderItemRequest.json")) {
            if (stream == null) {
                throw new IOException("Could not find orderItemRequest.json");
            }
            StringWriter writer = new StringWriter();
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (IOException e) {
            log.error("Failed to load test data", e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}
