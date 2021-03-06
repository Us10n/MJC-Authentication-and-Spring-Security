package com.epam.esm.service.service.impl;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.UserRole;
import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.mapper.UserMapper;
import com.epam.esm.service.service.UserService;
import com.epam.esm.service.util.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncode;

    /**
     * Instantiates a new User service.
     *
     * @param userDao the user dao
     */
    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncode = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto object) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();
        UserValidator.isUserRegisterDtoValid(object, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }
        if (userDao.findByEmail(object.getEmail()).isPresent()) {
            throw new DuplicateEntityException(USER_EXIST);
        }

        String encryptedPassword = bCryptPasswordEncode.encode(object.getPassword());
        object.setPassword(encryptedPassword);
        object.setRole(UserRole.USER);
        User userModel = UserMapper.INSTANCE.mapToEntity(object);
        User createdUser = userDao.create(userModel);

        return UserMapper.INSTANCE.mapToDto(createdUser);
    }

    @Override
    public PagedModel<UserDto> readAll(Integer page, Integer limit) {
        List<UserDto> userDtos = userDao.findAll(page, limit)
                .stream()
                .map(UserMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if (userDtos.isEmpty()) {
            throw new EmptyListRequestedException();
        }
        long totalNumberOfEntities = userDao.countAll();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);

        return PagedModel.of(userDtos, metadata);
    }

    @Override
    public UserDto readById(long id) {
        Optional<User> optionalUser = userDao.findById(id);
        User foundUser = optionalUser
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        return UserMapper.INSTANCE.mapToDto(foundUser);
    }

    @Override
    public UserDto readByEmail(String email) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();

        if (!UserValidator.isEmailValid(email)) {
            exceptionHolder.addException(BAD_USER_EMAIL, email);
            throw new IncorrectParameterException(exceptionHolder);
        }

        Optional<User> optionalUser = userDao.findByEmail(email);
        User foundUser = optionalUser
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        return UserMapper.INSTANCE.mapToDto(foundUser);
    }
}
