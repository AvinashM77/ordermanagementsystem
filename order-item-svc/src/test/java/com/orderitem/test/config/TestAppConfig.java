package com.orderitem.test.config;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.orderitem.dao.IOrderItemDAO;
import com.orderitem.dao.OrderItemDAOImpl;
import com.orderitem.service.OrderItemController;

@TestConfiguration
public class TestAppConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("db/sql/create-db.sql")
            .generateUniqueName(true)
            .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public IOrderItemDAO orderItemDAO(JdbcTemplate jdbcTemplate) {
        OrderItemDAOImpl dao = new OrderItemDAOImpl();
        dao.setJdbcTemplate(jdbcTemplate);
        return dao;
    }

    @Bean
    public OrderItemController orderItemController(IOrderItemDAO orderItemDAO) {
        return new OrderItemController(orderItemDAO);
    }
}