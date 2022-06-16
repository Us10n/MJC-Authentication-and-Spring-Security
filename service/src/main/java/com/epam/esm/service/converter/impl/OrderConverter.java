package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.stereotype.Component;

/**
 * The type Order converter.
 */
@Component
public class OrderConverter implements DtoEntityConverter<OrderDetailDto, OrderDetail> {

    @Override
    public OrderDetailDto convertToDto(OrderDetail object) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(object.getId());
        orderDetailDto.setPrice(object.getPrice());
        orderDetailDto.setPurchaseTime(object.getPurchaseTime());
        orderDetailDto.setGiftCertificateId(object.getGiftCertificate().getId());
        orderDetailDto.setUserId(object.getUser().getId());

        return orderDetailDto;
    }

    @Override
    public OrderDetail convertToEntity(OrderDetailDto object) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(object.getOrderId());
        orderDetail.setPrice(object.getPrice());
        orderDetail.setPurchaseTime(object.getPurchaseTime());

        return orderDetail;
    }
}
