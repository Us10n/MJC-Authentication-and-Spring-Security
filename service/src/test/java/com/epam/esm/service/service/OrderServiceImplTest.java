package com.epam.esm.service.service;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.OrderInputDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.repository.dao.impl.OrderDaoImpl;
import com.epam.esm.repository.dao.impl.UserDaoImpl;
import com.epam.esm.service.service.impl.OrderServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao = Mockito.mock(OrderDaoImpl.class);
    @Mock
    private UserDao userDao = Mockito.mock(UserDaoImpl.class);
    @Mock
    private GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");
    User sampleUser = new User(1, "Nick", null, null, null, new ArrayList<>());
    GiftCertificate sampleCertificate = new GiftCertificate(
            1, "test1", "test1", 1.1, 1, sampleDate, sampleDate, null
    );
    private OrderService orderService;
    private List<Order> orders;
    private List<OrderDto> orderDtos;

    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        orders.add(new Order(1, sampleUser, sampleDate, Lists.list(new OrderDetail(1, 1.1, sampleCertificate))));
        orderDtos = new ArrayList<>();
        orderDtos.add(new OrderDto(1, 1, sampleDate, Lists.list(new OrderDetailDto(1, 1.1, 1))));

        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(sampleUser));
        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(sampleCertificate));
        Mockito.when(orderDao.create(Mockito.any(Order.class))).thenReturn(orders.get(0));
        Mockito.when(orderDao.countAll()).thenReturn(1L);
        Mockito.when(orderDao.findAll(1, 10)).thenReturn(orders);
        Mockito.when(orderDao.findById(1)).thenReturn(Optional.of(orders.get(0)));
        Mockito.when(giftCertificateDao.countAll()).thenReturn(1L);
        Mockito.when(orderDao.findOrdersByUserId(1, 1, 10)).thenReturn(orders);

        orderService = new OrderServiceImpl(orderDao, userDao, giftCertificateDao);
    }

    @Test
    void create() {
        OrderInputDto order = new OrderInputDto(1L, Lists.list(1L));
        OrderDto actual = orderService.create(order);
        OrderDto expected = new OrderDto(1, 1, sampleDate, Lists.list(new OrderDetailDto(1, 1.1, 1)));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<OrderDto> actual = new ArrayList<>(orderService.readAll(1, 10).getContent());
        List<OrderDto> expected = orderDtos;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readById() {
        OrderDto actual = orderService.readById(1);
        OrderDto expected = new OrderDto(1, 1, sampleDate, Lists.list(new OrderDetailDto(1, 1.1, 1)));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readOrdersByUserId() {
        List<OrderDto> actual = new ArrayList<>(orderService.readOrdersByUserId(1, 1, 10).getContent());
        List<OrderDto> expected = orderDtos;

        Assertions.assertEquals(expected, actual);
    }
}