package com.epam.esm.service.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {
    public UserDetails readByEmail(String email);
}
