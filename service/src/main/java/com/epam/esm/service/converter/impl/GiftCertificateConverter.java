package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Gift certificate converter.
 */
@Component
public class GiftCertificateConverter implements DtoEntityConverter<GiftCertificateDto, GiftCertificate> {
    private final TagConverter tagConverter;

    /**
     * Instantiates a new Gift certificate converter.
     *
     * @param tagConverter the tag converter
     */
    @Autowired
    public GiftCertificateConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate object) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setGiftCertificateId(object.getId());
        giftCertificateDto.setName(object.getName());
        giftCertificateDto.setDescription(object.getDescription());
        giftCertificateDto.setPrice(object.getPrice());
        giftCertificateDto.setDuration(object.getDuration());
        giftCertificateDto.setCreateDate(object.getCreateDate());
        giftCertificateDto.setLastUpdateDate(object.getLastUpdateDate());
        List<TagDto> tags = object.getTags() == null
                ? new ArrayList<>()
                : object.getTags().stream().map(tagConverter::convertToDto).collect(Collectors.toList());
        giftCertificateDto.setTags(tags);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate convertToEntity(GiftCertificateDto object) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(object.getGiftCertificateId());
        giftCertificate.setName(object.getName());
        giftCertificate.setDescription(object.getDescription());
        giftCertificate.setPrice(object.getPrice());
        giftCertificate.setDuration(object.getDuration());
        giftCertificate.setCreateDate(object.getCreateDate());
        giftCertificate.setLastUpdateDate(object.getLastUpdateDate());
        Set<Tag> tags = object.getTags() == null
                ? new HashSet<>()
                : object.getTags().stream().map(tagConverter::convertToEntity).collect(Collectors.toSet());
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}
