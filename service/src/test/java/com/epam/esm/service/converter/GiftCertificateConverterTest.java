package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.service.converter.impl.GiftCertificateConverter;
import com.epam.esm.service.converter.impl.TagConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateConverterTest {

    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;
    private GiftCertificateConverter giftCertificateConverter = new GiftCertificateConverter(new TagConverter());

    @BeforeAll
    public void setup() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(1, "funny"));
        tags.add(new Tag(2, "useful"));
        tags.add(new Tag(3, "great"));

        List<TagDto> tagDtos = new ArrayList<>();
        tagDtos.add(new TagDto(1, "funny"));
        tagDtos.add(new TagDto(2, "useful"));
        tagDtos.add(new TagDto(3, "great"));

        LocalDateTime sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");

        certificate = new GiftCertificate(
                1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, tags
        );

        certificateDto = new GiftCertificateDto(
                1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, tagDtos
        );
    }

    @Test
    void convertToDto() {
        GiftCertificateDto actual = giftCertificateConverter.convertToDto(certificate);
        GiftCertificateDto expected = certificateDto;
        actual.getTags().sort(Comparator.comparing(TagDto::getTagId));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToEntity() {
        GiftCertificate actual = giftCertificateConverter.convertToEntity(certificateDto);
        GiftCertificate expected = certificate;

        Assertions.assertEquals(expected, actual);
    }
}