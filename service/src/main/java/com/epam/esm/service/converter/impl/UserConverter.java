package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.UserRole;
import com.epam.esm.domain.entity.User;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type User converter.
 */
@Component
public class UserConverter implements DtoEntityConverter<UserDto, User> {

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

        return user;
    }
}
