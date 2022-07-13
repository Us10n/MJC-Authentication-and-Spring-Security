package com.epam.esm.service.mapper;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDetailMapper {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(source = "orderDetail.id", target = "orderDetailId")
    @Mapping(source = "orderDetail.giftCertificate.id", target = "giftCertificateId")
    OrderDetailDto mapToDto(OrderDetail orderDetail);

    @Mapping(source = "orderDetailId", target = "id")
    OrderDetail mapToEntity(OrderDetailDto orderDetailDto);
}
