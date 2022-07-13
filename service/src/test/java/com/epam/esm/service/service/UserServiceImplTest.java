package com.epam.esm.service.service;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.domain.dto.UserRole;
import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.UserDaoImpl;
import com.epam.esm.service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;
    private UserService userService;
    List<User> userList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        userList.add(new User(1, "email@email.com", "qwerty1234", "Rick", UserRole.USER.toString(), null));
        userList.add(new User(2, "sam@email.com", "12345ada", "Sam", UserRole.USER.toString(), null));
        userList.add(new User(3, "test@test.com", "qwerty1234", "Rick", UserRole.USER.toString(), null));

        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(userList.get(0)));
        Mockito.when(userDao.findById(0)).thenReturn(Optional.empty());
        Mockito.when(userDao.findAll(1, 10)).thenReturn(userList);
        Mockito.when(userDao.create(Mockito.any(User.class))).thenReturn(userList.get(0));
        Mockito.when(userDao.findByEmail("test@test.com")).thenReturn(Optional.ofNullable(userList.get(2)));
        Mockito.when(userDao.findByEmail("email@email.com")).thenReturn(Optional.empty());

        userService = new UserServiceImpl(userDao, new BCryptPasswordEncoder());
    }

    @Test
    void create() {
        UserDto userDto = new UserDto(0, "email@email.com", "qwerty1234", "Rick", UserRole.USER);
        UserDto actual = userService.create(userDto);
        UserDto expected = new UserDto(1, "email@email.com", "qwerty1234", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readByEmail(){
        UserDto userDto = new UserDto(0, "test@test.com", "qwerty1234", "Rick", UserRole.USER);
        UserDto actual = userService.readByEmail("test@test.com");
        UserDto expected = new UserDto(3, "test@test.com", "qwerty1234", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        UserDto actual = new ArrayList<UserDto>(userService.readAll(1, 10).getContent()).get(0);
        UserDto expected = new UserDto(1, "email@email.com", "qwerty1234", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readById() {
        UserDto actual = userService.readById(1);
        UserDto expected = new UserDto(1, "email@email.com", "qwerty1234", "Rick", UserRole.USER);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test() {
        int i = 0;
        int j = 0;
        System.out.println(++i + ++j + i++);
    }
}