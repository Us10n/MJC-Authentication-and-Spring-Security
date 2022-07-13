package com.epam.esm.service.mapper;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.entity.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(uses = OrderDetailMapper.class)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    OrderDto mapToDto(Order order);

    @InheritInverseConfiguration
    @Mapping(target = "user",ignore = true)
    Order mapToEntity(OrderDto orderDto);
}
