package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.UserRole;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.converter.impl.OrderConverter;
import com.epam.esm.service.converter.impl.OrderDetailConverter;
import com.epam.esm.service.converter.impl.UserConverter;
import com.epam.esm.service.mapper.UserMapper;
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
    private UserConverter userConverter = new UserConverter(new OrderConverter(new OrderDetailConverter()));

    @BeforeAll
    public void setup() {
        LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");
        User userTmp = new User(1, "Rick", null, null, null, null);
        GiftCertificate giftCertificate = new GiftCertificate(
                1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, null
        );

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, userTmp, sampleDate, null));

        user = new User(1, "Rick@email.com", "password", "Rick", UserRole.USER.toString(), orders);
        userDto = new UserDto(1, "Rick@email.com", "password", "Rick", UserRole.USER);
    }

    @Test
    void convertToDto() {
        UserDto actual = userConverter.convertToDto(user);
        UserDto expected = new UserDto(1, "Rick@email.com", "password", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test(){
        UserDto actual = UserMapper.INSTANCE.mapToDto(user);
        UserDto expected = new UserDto(1, "Rick@email.com", "password", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void test2(){
        User actual = UserMapper.INSTANCE.mapToEntity(userDto);
        User expected = new User(1, "Rick@email.com", "password", "Rick", UserRole.USER.toString(), null);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToEntity() {
        User actual = userConverter.convertToEntity(userDto);
        User expected = new User(1, "Rick@email.com", "password", "Rick", UserRole.USER.toString(), null);

        Assertions.assertEquals(expected, actual);
    }
}