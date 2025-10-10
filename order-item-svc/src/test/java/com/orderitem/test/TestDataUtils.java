/**
 * 
 */
package com.orderitem.test;

import com.google.gson.Gson;
import com.orderitem.model.OrderItemRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Avinash
 *
 */
public class TestDataUtils {

    public static OrderItemRequest prepareRequest() {
        try (InputStream stream = TestDataUtils.class.getClassLoader()
                .getResourceAsStream("orderItemRequest.json")) {
            StringWriter writer = new StringWriter();
            Assertions.assertNotNull(stream);
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            String json = writer.toString();
            return new Gson().fromJson(json, OrderItemRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }

}
