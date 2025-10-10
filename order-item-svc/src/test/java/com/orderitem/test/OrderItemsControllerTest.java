package com.orderitem.test;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/sql/ddl.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OrderItemsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    void createOrderItemTest() throws Exception {
        String requestBody = prepareRequest("orderItemRequest.json");
        mvc.perform(post("/orderitem")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("SUCCESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OrderItems Added Successfully"))
            .andDo(print());
    }

    @Test
    @Order(2)
    void getOrderItems() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orderitem/{orderId}", "f809a7ea-9be2-41ca-b112-0adf2cba5e04")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2));
    }

    @Test
    @Order(3)
    void createOrderItemTest_400_BadRequest() throws Exception {
        String requestBody = "";
        mvc.perform(post("/orderitem")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @Order(4)
    void createOrderItemTest_Failure() throws Exception {
        String requestBody = prepareRequest("orderItemRequest2.json");
        mvc.perform(post("/orderitem")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("FAILURE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Could not add OrderItems"))
                .andDo(print());
    }

    private String prepareRequest(String fileName) {
        try (InputStream stream = OrderItemsControllerTest.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IOException("Could not find orderItemRequest.json");
            }
            StringWriter writer = new StringWriter();
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}
