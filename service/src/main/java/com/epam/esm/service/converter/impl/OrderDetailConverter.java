package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailConverter implements DtoEntityConverter<OrderDetailDto, OrderDetail> {
    @Override
    public OrderDetailDto convertToDto(OrderDetail object) {
        OrderDetailDto orderDetailDto=new OrderDetailDto();
        orderDetailDto.setOrderDetailId(object.getId());
        orderDetailDto.setPrice(object.getPrice());
        orderDetailDto.setGiftCertificateId(object.getGiftCertificate().getId());
        return orderDetailDto;
    }

    @Override
    public OrderDetail convertToEntity(OrderDetailDto object) {
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setId(object.getOrderDetailId());
        orderDetail.setPrice(object.getPrice());
        return orderDetail;
    }
}
