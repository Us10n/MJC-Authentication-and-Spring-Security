package com.epam.esm.repository.dao;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@SpringBootApplication
class GiftCertificateDaoTest {

    @Autowired
    GiftCertificateDao giftCertificateDao;

    @Test
    void create() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest1", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate actual = giftCertificateDao.create(sample);
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findById() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest2", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate expected = giftCertificateDao.create(sample);

        Optional<GiftCertificate> actual = giftCertificateDao.findById(expected.getId());
        Assertions.assertEquals(expected, actual.get());
    }

    @Test
    @Transactional
    void findByName() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest3", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate expected = giftCertificateDao.create(sample);

        Optional<GiftCertificate> actual = giftCertificateDao.findByName(expected.getName());
        Assertions.assertEquals(expected, actual.get());
    }

    @Test
    @Transactional
    void findAll() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest4", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate created = giftCertificateDao.create(sample);

        boolean actual = giftCertificateDao.findAll(1, 10).stream().anyMatch(giftCertificate -> giftCertificate.getId() == created.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void countAll() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long before = giftCertificateDao.countAll();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest5", "test", 1.1, 1, localDateTime, localDateTime, tags);
        giftCertificateDao.create(sample);

        long after = giftCertificateDao.countAll();
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    void countAllByCriteria() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long before = giftCertificateDao.countAll();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest6", "test", 1.1, 1, localDateTime, localDateTime, tags);
        giftCertificateDao.create(sample);

        long after = giftCertificateDao.countAllByCriteria(new GiftCertificateCriteria());
        Assertions.assertEquals(before + 1, after);
    }

    @Test
    @Transactional
    void findByCriteria() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest7", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate created = giftCertificateDao.create(sample);

        boolean actual = giftCertificateDao.findByCriteria(new GiftCertificateCriteria(), 1, 10)
                .stream()
                .anyMatch(giftCertificate -> giftCertificate.getId() == created.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    @Transactional
    void deleteById() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long before = giftCertificateDao.countAll();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest8", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate created = giftCertificateDao.create(sample);
        giftCertificateDao.deleteById(created.getId());

        long after = giftCertificateDao.countAll();
        Assertions.assertEquals(before, after);
    }

    @Test
    @Transactional
    void update() {
        LocalDateTime localDateTime = LocalDateTime.now();

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag(0, "hello"));
        tags.add(new Tag(0, "world"));
        GiftCertificate sample = new GiftCertificate(0, "createTest9", "test", 1.1, 1, localDateTime, localDateTime, tags);
        GiftCertificate created = giftCertificateDao.create(sample);

        GiftCertificate expected = new GiftCertificate(created.getId(), "createTest9", "test", 1.1, 1, localDateTime, localDateTime, null);
        GiftCertificate actual = giftCertificateDao.update(expected);
        Assertions.assertEquals(expected, actual);
    }
}