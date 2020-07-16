package com.orderitem.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.orderitem.OrderItemApp;
import com.orderitem.test.config.TestAppConfig;

/**
 * @author Avinash
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderItemApp.class)
@ContextConfiguration(classes = TestAppConfig.class)
@WebAppConfiguration
public class OrderItemsControllerTest {

	private MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void createOrderItemTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/orderitem").content(prepareRequest())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
	}

	@Test
	public void getOrderItems() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/orderitem/{orderId}", "f809a7ea-9be2-41ca-b112-0adf2cba5e04")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2));
	}

	private String prepareRequest() {
		try (InputStream stream = OrderItemsControllerTest.class.getClassLoader()
				.getResourceAsStream("orderItemRequest.json")) {
			StringWriter writer = new StringWriter();
			IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
