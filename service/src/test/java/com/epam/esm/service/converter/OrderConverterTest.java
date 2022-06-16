package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.converter.impl.OrderConverter;
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

    private OrderDetail orderDetail;
    private OrderDetailDto orderDetailDto;
    private OrderConverter orderConverter = new OrderConverter();
    private User sampleUser;
    private GiftCertificate sampleCertificate;

    @BeforeAll
    public void setup() {
        LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");

        sampleUser = new User(1, "Rick", null);
        sampleCertificate = new GiftCertificate(1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, null);
        orderDetail = new OrderDetail(1, 1.1, sampleDate, sampleUser,sampleCertificate);
        orderDetailDto=new OrderDetailDto(1,1.1,sampleDate,1,1);
    }

    @Test
    void convertToDto() {
        OrderDetailDto actual = orderConverter.convertToDto(orderDetail);
        OrderDetailDto expected = orderDetailDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToEntity() {
        OrderDetail actual = orderConverter.convertToEntity(orderDetailDto);
        OrderDetail expected = new OrderDetail(1, 1.1, LocalDateTime.parse("2022-04-11T10:00:11.156"), null,null);

        Assertions.assertEquals(expected, actual);
    }
}