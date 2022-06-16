package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@SpringBootApplication
class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    @Test
    void create() {
        LocalDateTime localDateTime = LocalDateTime.now();

        OrderDetail sample = new OrderDetail(0, 1.1, localDateTime, null,null);
        OrderDetail actual = orderDao.create(sample);
        Assertions.assertEquals(sample, actual);
    }

    @Test
    void findById() {
        LocalDateTime localDateTime = LocalDateTime.now();

        OrderDetail sample = new OrderDetail(0, 1.1, localDateTime, null,null);
        OrderDetail created = orderDao.create(sample);
        OrderDetail actual=orderDao.findById(created.getId()).get();

        Assertions.assertEquals(created, actual);
    }

    @Test
    void findAll() {
        LocalDateTime localDateTime = LocalDateTime.now();

        OrderDetail sample = new OrderDetail(0, 1.1, localDateTime, null,null);
        OrderDetail created = orderDao.create(sample);
        boolean actual=orderDao.findAll(1,10).stream().anyMatch(order -> order.getId() == created.getId());

        Assertions.assertTrue(actual);
    }

    @Test
    void countAll() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long before = orderDao.countAll();

        OrderDetail sample = new OrderDetail(0, 1.1, localDateTime, null,null);
        OrderDetail actual = orderDao.create(sample);

        long after = orderDao.countAll();
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    void findOrdersByUserId() {
        LocalDateTime localDateTime = LocalDateTime.now();

        OrderDetail sample = new OrderDetail(0, 1.1, localDateTime, null,null);
        OrderDetail created = orderDao.create(sample);
        boolean actual=orderDao.findOrdersByUserId(1,1,10).isEmpty();

        Assertions.assertTrue(actual);
    }
}