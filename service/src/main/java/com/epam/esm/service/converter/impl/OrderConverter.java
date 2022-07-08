package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Order converter.
 */
@Component
public class OrderConverter implements DtoEntityConverter<OrderDto, Order> {

    @Autowired
    private OrderDetailConverter orderDetailConverter;

    @Override
    public OrderDto convertToDto(Order object) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(object.getId());
        orderDto.setUserId(object.getUser().getId());
        orderDto.setPurchaseTime(object.getPurchaseTime());

        List<OrderDetailDto> orderDetailDtos = object.getOrderDetails()
                .stream()
                .map(orderDetailConverter::convertToDto)
                .collect(Collectors.toList());
        orderDto.setOrderDetailDtos(orderDetailDtos);

        return orderDto;
    }

    @Override
    public Order convertToEntity(OrderDto object) {
        Order order = new Order();
        order.setId(object.getOrderId());
        order.setPurchaseTime(object.getPurchaseTime());

        List<OrderDetail> orderDetail = object.getOrderDetailDtos()
                .stream()
                .map(orderDetailConverter::convertToEntity)
                .collect(Collectors.toList());
        order.setOrderDetails(orderDetail);

        return order;
    }
}
