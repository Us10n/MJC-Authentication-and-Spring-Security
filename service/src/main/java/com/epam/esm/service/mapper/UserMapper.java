package com.epam.esm.service.mapper;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id",target = "userId")
    UserDto mapToDto(User user);
    @InheritInverseConfiguration
    User mapToEntity(UserDto userDto);
}
