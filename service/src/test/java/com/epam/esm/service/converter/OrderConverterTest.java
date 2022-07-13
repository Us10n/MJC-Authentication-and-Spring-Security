package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.mapper.OrderMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderConverterTest {

    private Order order1;
    private Order order2;
    private OrderDto orderDto;

    @BeforeAll
    public void setup() {
        LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");

        User sampleUser = new User(1, "Rick", null, null, null, null);
        GiftCertificate sampleCertificate = new GiftCertificate(1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, null);
        order1 = new Order(1, sampleUser, sampleDate, Lists.list(new OrderDetail(1, 1.2, sampleCertificate)));
        order2 = new Order(1, null, sampleDate, Lists.list(new OrderDetail(1, 1.2, null)));
        orderDto = new OrderDto(1, 1,sampleDate, Lists.list(new OrderDetailDto(1,1.2,1)));
    }

    @Test
    void convertToDto(){
        OrderDto actual = OrderMapper.INSTANCE.mapToDto(order1);
        OrderDto expected = orderDto;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void convertToEntity(){
        Order actual = OrderMapper.INSTANCE.mapToEntity(orderDto);
        Order expected = order2;

        Assertions.assertEquals(expected, actual);
    }
}