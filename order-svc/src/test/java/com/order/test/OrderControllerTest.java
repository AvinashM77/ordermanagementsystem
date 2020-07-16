package com.order.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.order.OrderApp;
import com.order.model.BaseResponse;
import com.order.test.config.TestAppConfig;
import com.order.test.utils.TestDataUtils;

/**
 * @author Avinash
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderApp.class)
@ContextConfiguration(classes = TestAppConfig.class)
@WebAppConfiguration
public class OrderControllerTest {

	private MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	private static String orderId;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void createOrderTest() throws Exception {
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/order").content(TestDataUtils.prepareRequestAsString())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").exists()).andReturn();
		BaseResponse response = new Gson().fromJson(result.getResponse().getContentAsString(), BaseResponse.class);
		orderId = response.getReferenceId();
	}

	@Test
	public void getOrderInfo() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/order/{orderId}", orderId).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems").exists());
	}

}