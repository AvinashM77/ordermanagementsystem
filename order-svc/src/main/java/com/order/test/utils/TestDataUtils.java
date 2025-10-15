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

	public static String prepareRequestAsString(String fileName) throws IOException {
		try (InputStream stream = TestDataUtils.class.getClassLoader().getResourceAsStream(fileName)) {
			StringWriter writer = new StringWriter();
            assert stream != null;
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			return writer.toString();
		}
    }

	public static OrderRequest prepareRequest() throws IOException {
		try (InputStream stream = TestDataUtils.class.getClassLoader().getResourceAsStream("OrderRequest.json")) {
			StringWriter writer = new StringWriter();
            assert stream != null;
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
			return new Gson().fromJson(writer.toString(), OrderRequest.class);
		}
    }

}
