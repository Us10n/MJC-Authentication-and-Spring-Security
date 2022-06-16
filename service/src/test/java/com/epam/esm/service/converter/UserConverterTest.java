package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.converter.impl.OrderConverter;
import com.epam.esm.service.converter.impl.UserConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserConverterTest {

    private User user;
    private UserDto userDto;
    private UserConverter userConverter = new UserConverter(new OrderConverter());

    @BeforeAll
    public void setup() {
        LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");
        User userTmp = new User(1, "Rick", null);
        GiftCertificate giftCertificate = new GiftCertificate(
                1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, null
        );

        List<OrderDetail> orders = new ArrayList<>();
        orders.add(new OrderDetail(1, 1.1, sampleDate, userTmp, giftCertificate));

        List<OrderDetailDto> orderDtos = new ArrayList<>();
        orderDtos.add(new OrderDetailDto(1, 1.1, sampleDate, 1, 1));


        user = new User(1, "Rick", orders);
        userDto = new UserDto(1, "Rick", orderDtos);
    }

    @Test
    void convertToDto() {
        UserDto actual = userConverter.convertToDto(user);
        UserDto expected = userDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToEntity() {
        Assertions.assertDoesNotThrow(()->userConverter.convertToEntity(userDto));
    }
}