package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.UserAuthInfo;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.service.UserService;
import com.epam.esm.web.hateoas.impl.UserHateoasAdder;
import com.epam.esm.web.security.JwtUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private UserService userService;
    private UserHateoasAdder userHateoasAdder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(UserService userService, UserHateoasAdder hateoasAdder,
                                    AuthenticationManager manager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userHateoasAdder = hateoasAdder;
        this.authenticationManager = manager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/auth")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> authorizeUser(@RequestBody UserAuthInfo authInfo) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authInfo.getLogin(), authInfo.getPassword()));


        String token = jwtUtil.generateToken(authInfo.getLogin());
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);

        return map;
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        userHateoasAdder.addLinksToEntity(createdUser);
        return createdUser;
    }
}
