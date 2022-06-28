package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.UserAuthInfo;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.service.UserService;
import com.epam.esm.web.hateoas.impl.UserHateoasAdder;
import com.epam.esm.web.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private UserService userService;
    private UserHateoasAdder userHateoasAdder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService,
                                    UserHateoasAdder hateoasAdder, AuthenticationManager manager) {
        this.userService = userService;
        this.userHateoasAdder = hateoasAdder;
        this.authenticationManager = manager;
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public String authorizeUser(@RequestBody UserAuthInfo authInfo) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authInfo.getLogin(), authInfo.getPassword()));

        return JwtUtil.generateToken(authInfo.getLogin());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDto authorizeUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        userHateoasAdder.addLinksToEntity(createdUser);
        return userDto;
    }
}
