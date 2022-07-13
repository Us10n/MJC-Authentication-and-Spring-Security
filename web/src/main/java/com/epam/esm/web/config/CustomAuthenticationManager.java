package com.epam.esm.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;

    @Autowired
    public CustomAuthenticationManager(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String enteredUsername = authentication.getPrincipal() + "";
        String enteredPassword = authentication.getCredentials() + "";

        UserDetails userDetails = userDetailsService.loadUserByUsername(enteredUsername);
        String foundUsername = userDetails.getUsername();
        String foundPassword = userDetails.getPassword();
        if (!enteredUsername.equals(foundUsername) || !BCrypt.checkpw(enteredPassword, foundPassword)) {
            throw new BadCredentialsException("User authentication failed.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
