package com.epam.esm.web.security;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.converter.impl.UserConverter;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private UserService userService;
    private UserConverter userConverter;

    @Autowired
    public JwtFilter(UserService userService, UserConverter userConverter) {
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && JwtUtil.isTokenValid(token)) {
            String login = JwtUtil.getLoginFromToken(token);

            UserDto userDto = userService.readByEmail(login);
            UserDetails userDetails = userConverter.convertToDetails(userDto);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String rawToken = request.getHeader(AUTHORIZATION);

        return hasText(rawToken) && rawToken.startsWith(BEARER)
                ? rawToken.substring(7)
                : null;
    }


}
