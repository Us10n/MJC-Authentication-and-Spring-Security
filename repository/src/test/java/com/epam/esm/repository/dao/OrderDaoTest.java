package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.config.TestConfig;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    LocalDateTime localDateTime = LocalDateTime.parse("2022-04-11T10:00:11.156");
    User user = new User(0, "email@email.com", "awdawdawd213123asd", "Kilo", "ADMIN", null);
    GiftCertificate certificate = new GiftCertificate(0, "test", "testd", 1.1, 10, localDateTime, localDateTime, null);

    @Test
    @Transactional
    void create() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Order sample = new Order(0, user, localDateTime, Lists.list(new OrderDetail(0, 1.1, certificate)));
        Order actual = orderDao.create(sample);
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findById() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Order sample = new Order(0, user, localDateTime, Lists.list(new OrderDetail(0, 1.1, certificate)));
        Order created = orderDao.create(sample);
        Order actual = orderDao.findById(created.getId()).get();

        Assertions.assertEquals(created, actual);
    }

    @Test
    @Transactional
    void findAll() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Order sample = new Order(0, user, localDateTime, Lists.list(new OrderDetail(0, 1.1, certificate)));
        Order created = orderDao.create(sample);
        List<Order> orderList=orderDao.findAll(1, 10);
        boolean actual = orderList.stream().anyMatch(order -> order.getId() == created.getId());

        Assertions.assertTrue(actual);
    }

    @Test
    @Transactional
    void countAll() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long before = orderDao.countAll();

        Order sample = new Order(0, user, localDateTime, Lists.list(new OrderDetail(0, 1.1, certificate)));
        Order actual = orderDao.create(sample);

        long after = orderDao.countAll();
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    @Transactional
    void findOrdersByUserId() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Order sample = new Order(0, user, localDateTime, Lists.list(new OrderDetail(0, 1.1, certificate)));
        Order created = orderDao.create(sample);
        boolean actual = orderDao.findOrdersByUserId(1, 1, 10).isEmpty();

        Assertions.assertTrue(actual);
    }
}