package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = TestConfig.class)
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void create() {
        User sample = new User(0, "email1", "pass", "name", "USER", null);
        User actual = userDao.create(sample);
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findById() {
        User sample = new User(0, "email2", "pass", "name", "USER", null);
        User created = userDao.create(sample);
        User actual = userDao.findById(created.getId()).get();
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findByEmail() {
        User sample = new User(0, "email3", "pass", "name", "USER", null);
        User created = userDao.create(sample);
        User actual = userDao.findByEmail(created.getEmail()).get();
        Assertions.assertEquals(sample, actual);
    }

    @Test
    @Transactional
    void findAll() {
        User sample = new User(0, "email4", "pass", "name", "USER", null);
        User created = userDao.create(sample);
        boolean actual=userDao.findAll(1,10).stream().anyMatch(tag -> tag.getId() == created.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void countAll() {
        long before = userDao.countAll();

        User sample = new User(0, "email5", "pass", "name", "USER", null);
        User created = userDao.create(sample);
        long after = userDao.countAll();
        Assertions.assertEquals(before+1,after);
    }
}