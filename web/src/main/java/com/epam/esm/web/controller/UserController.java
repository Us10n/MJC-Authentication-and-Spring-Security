package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.service.UserService;
import com.epam.esm.web.hateoas.impl.UserHateoasAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserHateoasAdder userHateoasAdder;

    @Autowired
    public UserController(UserService userService, UserHateoasAdder hateoasAdder) {
        this.userService = userService;
        this.userHateoasAdder = hateoasAdder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDto> readAllUsers(@RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                            @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {
        PagedModel<UserDto> userDtos = userService.readAll(page, limit);
        userHateoasAdder.addLinksToCollection(userDtos);
        return userDtos;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto readUserById(@PathVariable long id) {
        UserDto userDto = userService.readById(id);
        userHateoasAdder.addLinksToEntity(userDto);
        return userDto;
    }
}
