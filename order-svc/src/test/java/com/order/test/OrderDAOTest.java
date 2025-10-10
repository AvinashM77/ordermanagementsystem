package com.order.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.order.OrderApp;
import com.order.dao.IAddressDAO;
import com.order.dao.IOrderDAO;
import com.order.exception.OrderBaseException;
import com.order.model.Order;
import com.order.model.OrderRequest;
import com.order.model.OrderResponse;
import com.order.test.config.TestAppConfig;
import com.order.test.utils.TestDataUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OrderApp.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class OrderDAOTest {

	private static final Logger log = LogManager.getLogger(OrderDAOTest.class);

	@Autowired
	IOrderDAO iOrderDAO;

	@Autowired
	IAddressDAO iAddressDAO;

	private static String addressId;
	private static String orderId;

	/**
	 * create Address test.
	 */
	@Test
	public void createAddressTestA() {
		addressId = iAddressDAO.save(TestDataUtils.prepareRequest().getShippingAddress());
		log.info(" createAddressTest/addressId {}", addressId);
		assertNotNull(addressId);
	}

	/**
	 * create order test.
	 * 
	 * @throws OrderBaseException
	 */
	@Test
	public void createOrderTestB() throws OrderBaseException {
		OrderRequest orderRequest = TestDataUtils.prepareRequest();
		orderId = iOrderDAO.save(new Order(orderRequest.getCustomerName(), addressId, orderRequest.getAmount()));
		log.info(" createOrderTest/orderId {}", orderId);
		assertNotNull(orderId);
	}

	/**
	 * get order test.
	 * 
	 * @throws OrderBaseException
	 */
	@Test
	public void getOrderTestC() throws OrderBaseException {
		OrderResponse orderResponse = iOrderDAO.get(orderId);
		assertNotNull(orderResponse);
	}

}