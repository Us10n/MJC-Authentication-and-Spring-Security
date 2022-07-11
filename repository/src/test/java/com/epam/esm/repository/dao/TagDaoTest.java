package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
class TagDaoTest {

    @Autowired
    TagDao tagDao;

    @Test
    void create() {
        Tag sample = new Tag(0, "world1");
        Tag actual = tagDao.create(sample);
        Assertions.assertEquals(sample, actual);
    }

    @Test
    void findById() {
        Tag sample = new Tag(0, "world2");
        Tag created = tagDao.create(sample);
        Tag actual = tagDao.findById(created.getId()).get();
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findByName() {
        Tag sample = new Tag(0, "world3");
        Tag created = tagDao.create(sample);
        Tag actual = tagDao.findByName(created.getName()).get();
        Assertions.assertEquals(sample, actual);
    }

    @Test
    void findAll() {
        Tag sample = new Tag(0, "world4");
        Tag created = tagDao.create(sample);
        boolean actual=tagDao.findAll(1,10).stream().anyMatch(tag -> tag.getId() == created.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void countAll() {
        long before = tagDao.countAll();

        Tag sample = new Tag(0, "world5");
        Tag created = tagDao.create(sample);
        long after = tagDao.countAll();
        Assertions.assertEquals(before+1,after);
    }

    @Test
    void deleteById() {
        long before = tagDao.countAll();

        Tag sample = new Tag(0, "world6");
        Tag created = tagDao.create(sample);
        tagDao.deleteById(created.getId());
        long after = tagDao.countAll();
        Assertions.assertEquals(before,after);
    }
}