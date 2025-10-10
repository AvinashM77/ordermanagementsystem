package com.order.test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.gson.Gson;
import com.order.model.BaseResponse;
import com.order.test.utils.TestDataUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Avinash
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/sql/ddl-orders.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@EnableWireMock
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    private static String orderId;

    @Test
    @Order(1)
    public void createOrderTest() throws Exception {

        // Given: The external API call is mocked with WireMock
        stubFor(WireMock.post(urlEqualTo("/orderitem"))
                .withRequestBody(matchingJsonPath("$.orderId", matching("[a-f0-9\\-]{36}")))
                .withRequestBody(matchingJsonPath("$.orderItems[0].productCode", equalTo("R01")))
                .withRequestBody(matchingJsonPath("$.orderItems[0].productName", equalTo("Strong Perfume")))
                .withRequestBody(matchingJsonPath("$.orderItems[0].quantity", equalTo("2")))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody("{\"result\": \"SUCCESS\", \"message\": \"OrderItems Added Successfully\"}")));

        MvcResult result = mvc
                .perform(
                        post("/order")
                                .content(TestDataUtils.prepareRequestAsString("OrderRequest.json"))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists()).andReturn();
        BaseResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), BaseResponse.class);
        orderId = response.getReferenceId();
    }

    @Test
    @Order(2)
    public void getOrderInfo() throws Exception {
        // Given: The external API call is mocked with WireMock
        stubFor(WireMock.get(urlPathMatching("/orderitem/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(
                                "{\"orderId\": \"" + orderId + "\", \"orderItems\": [{\"productCode\": \"R01\", \"productName\": \"Strong Perfume\", \"quantity\": 2}, {\"productCode\": \"G01\", \"productName\": \"Luxury Bag\", \"quantity\": 1}], \"count\": 2}")));

        mvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", orderId).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems").exists());
    }

}