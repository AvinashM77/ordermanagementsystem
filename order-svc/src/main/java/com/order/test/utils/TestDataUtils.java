/**
 * 
 */
package com.order.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.order.model.OrderRequest;

/**
 * @author Avinash
 *
 */
public class TestDataUtils {

	/**
	 * @return
	 */
	public static String prepareRequestAsString() {
		try (InputStream stream = TestDataUtils.class.getClassLoader().getResourceAsStream("orderRequest.json")) {
			StringWriter writer = new StringWriter();
			IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return
	 */
	public static OrderRequest prepareRequest() {
		try (InputStream stream = TestDataUtils.class.getClassLoader().getResourceAsStream("orderRequest.json")) {
			StringWriter writer = new StringWriter();
			IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			return new Gson().fromJson(writer.toString(), OrderRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
