package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.UserRole;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User converter.
 */
@Component
public class UserConverter implements DtoEntityConverter<UserDto, User> {

    private static final String ROLE_PREFIX = "ROLE_";

    /**
     * The Order converter.
     */
    final OrderConverter orderConverter;

    /**
     * Instantiates a new User converter.
     *
     * @param orderConverter the order converter
     */
    @Autowired
    public UserConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Override
    public UserDto convertToDto(User object) {
        UserDto userDto = new UserDto();
        userDto.setUserId(object.getId());
        userDto.setEmail(object.getEmail());
        userDto.setPassword(object.getPassword());
        userDto.setName(object.getName());
        userDto.setRole(UserRole.valueOf(object.getRole()));
        //todo should I remove orders from user dto?
        if (object.getOrders() != null) {
            List<OrderDetailDto> orders = object.getOrders().stream()
                    .map(orderConverter::convertToDto)
                    .collect(Collectors.toList());
            userDto.setOrders(orders);
        }
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto object) {
        User user = new User();
        user.setId(object.getUserId());
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setName(object.getName());
        user.setRole(object.getRole().toString());
        //todo should I remove orders from user dto?
        if (object.getOrders() != null) {
            List<OrderDetail> orders = object.getOrders().stream()
                    .map(orderConverter::convertToEntity)
                    .collect(Collectors.toList());
            user.setOrders(orders);
        }
        return user;
    }

    public UserDetails convertToDetails(UserDto object) {
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX
                + object.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(object.getEmail(), object.getPassword(), authorities);
    }
}
