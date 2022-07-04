package com.epam.esm.service.service.impl;

import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.NoSuchElementException;
import com.epam.esm.service.service.UserDetailService;
import com.epam.esm.service.util.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.service.exception.ExceptionMessageKey.BAD_USER_EMAIL;
import static com.epam.esm.service.exception.ExceptionMessageKey.USER_NOT_FOUND;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserDao userDao;

    @Autowired
    public UserDetailServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails readByEmail(String email) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();

        if (!UserValidator.isEmailValid(email)) {
            exceptionHolder.addException(BAD_USER_EMAIL, email);
            throw new IncorrectParameterException(exceptionHolder);
        }

        Optional<User> optionalUser = userDao.findByEmail(email);
        User foundUser = optionalUser
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX
                + foundUser.getRole()));

        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), authorities);
    }
}
