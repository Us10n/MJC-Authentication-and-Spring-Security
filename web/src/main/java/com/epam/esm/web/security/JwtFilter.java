package com.epam.esm.web.security;

import com.epam.esm.domain.dto.OrderInputDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.exception.NoSuchElementException;
import com.epam.esm.service.service.UserDetailService;
import com.epam.esm.service.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    private UserDetailService userDetailService;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(UserDetailService userDetailService, UserService userService,
                     JwtUtil jwtUtil) {
        this.userDetailService = userDetailService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.isTokenValid(token)) {
            String login = jwtUtil.getLoginFromToken(token);
            UserDetails userDetails = userDetailService.readByEmail(login);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            OrderInputDto orderInputDto = new Gson().fromJson(request.getReader(), OrderInputDto.class);
            if (orderInputDto.getUserId() != null
                    && !isAuthenticatedUserCreateOrder(orderInputDto.getUserId(), login)) {
                throw new AccessDeniedException("");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String rawToken = request.getHeader(AUTHORIZATION);
        return hasText(rawToken)
                ? rawToken
                : null;
    }

    private boolean isAuthenticatedUserCreateOrder(Long requestedUserId, String customerEmail) {
        UserDto customer = userService.readByEmail(customerEmail);
        return requestedUserId.equals(customer.getUserId());
    }


}
