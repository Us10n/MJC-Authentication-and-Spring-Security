package com.epam.esm.service.service;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.repository.dao.impl.TagDaoImpl;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.*;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    @Mock
    private TagDao tagDao = Mockito.mock(TagDaoImpl.class);

    private GiftCertificateService giftCertificateService;
    private GiftCertificateDto sampleDto;
    private LocalDateTime sampleDate;
    private GiftCertificateDto updatedDto;
    Set<Tag> tags = new HashSet<>();

    @BeforeAll
    public void setup() {
        tags.add(new Tag(1, "funny"));
        tags.add(new Tag(2, "useful"));
        tags.add(new Tag(3, "great"));

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        sampleDate = LocalDateTime.parse("2022-04-11T10:00:11.156");

        GiftCertificate sample = new GiftCertificate(
                1, "test1", "test1", 1.2, 1, sampleDate, sampleDate, tags
        );
        GiftCertificate updatedSample = new GiftCertificate(
                1, "upd", "upd", 1.1, 1, sampleDate, sampleDate, null
        );

        giftCertificates.add(sample);

        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(sample));
        Mockito.when(giftCertificateDao.findAll(1, 10)).thenReturn(giftCertificates);
        Mockito.when(giftCertificateDao.findByCriteria(new GiftCertificateCriteria(), 1, 10)).thenReturn(giftCertificates);

        Mockito.when(giftCertificateDao.update(Mockito.any(GiftCertificate.class))).thenReturn(updatedSample);
        Mockito.when(giftCertificateDao.create(Mockito.any(GiftCertificate.class))).thenReturn(sample);

        Mockito.when(giftCertificateDao.countAll()).thenReturn(1L);
        Mockito.when(giftCertificateDao.countAllByCriteria(Mockito.any(GiftCertificateCriteria.class))).thenReturn(1L);

        tags.remove(2);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao);

        sampleDto = GiftCertificateMapper.INSTANCE.mapToDto(sample);
        updatedDto = GiftCertificateMapper.INSTANCE.mapToDto(updatedSample);
    }

    @Test
    void create() {
        GiftCertificateDto actual = giftCertificateService.create(sampleDto);
        sampleDto.setCreateDate(sampleDate);
        sampleDto.setLastUpdateDate(sampleDate);
        Assertions.assertEquals(sampleDto, actual);
    }

    @Test
    void readAll() {
        PagedModel<GiftCertificateDto> readCertificates = giftCertificateService.readAll(1, 10);
        GiftCertificateDto expected = sampleDto;
        GiftCertificateDto actual = new ArrayList<>(readCertificates.getContent()).get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readById() {
        GiftCertificateDto actual = giftCertificateService.readById(1);
        Assertions.assertEquals(sampleDto, actual);
    }

    @Test
    void readByCriteria() {
        PagedModel<GiftCertificateDto> readCertificates = giftCertificateService.readByCriteria(new GiftCertificateCriteria(), 1, 10);
        GiftCertificateDto expected = sampleDto;
        GiftCertificateDto actual = new ArrayList<>(readCertificates.getContent()).get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deletePositive() {
        Assertions.assertDoesNotThrow(() -> {
            giftCertificateService.delete(1);
        });
    }

    @Test
    void update() {
        GiftCertificateDto newCertificate = new GiftCertificateDto(
                1, "upd", "upd", 1.1, 1, sampleDate, sampleDate, null
        );
        GiftCertificateDto actual = giftCertificateService.update(newCertificate);
        GiftCertificateDto expected = updatedDto;
        Assertions.assertEquals(expected, actual);
    }
}
