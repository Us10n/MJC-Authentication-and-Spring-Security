package com.epam.esm.service.mapper;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.entity.GiftCertificate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TagMapper.class)
public interface GiftCertificateMapper {

    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    @Mapping(source = "id",target = "giftCertificateId")
    GiftCertificateDto mapToDto(GiftCertificate giftCertificate);
    @InheritInverseConfiguration
    GiftCertificate mapToEntity(GiftCertificateDto giftCertificateDto);

}
