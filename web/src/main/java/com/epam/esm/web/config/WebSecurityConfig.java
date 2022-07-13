package com.epam.esm.web.config;

import com.epam.esm.web.exception.AccessDeniedHandlerEntryPoint;
import com.epam.esm.web.exception.AuthenticationHandlerEntryPoint;
import com.epam.esm.web.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String ADMIN = "ADMIN";

    private final JwtFilter jwtFilter;
    private final AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint;
    private final AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint;


    @Autowired
    public WebSecurityConfig(JwtFilter jwtFilter, AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint,
                             AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.authenticationHandlerEntryPoint = authenticationHandlerEntryPoint;
        this.accessDeniedHandlerEntryPoint = accessDeniedHandlerEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //Guest
                .antMatchers(HttpMethod.GET, "/giftCertificates/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth", "/register").permitAll()
                //User
                .antMatchers(HttpMethod.GET, "/tags/**", "/users/**", "/orders/**").fullyAuthenticated()
                .antMatchers(HttpMethod.POST, "/orders").fullyAuthenticated()
                //Admin
                .anyRequest().hasRole(ADMIN)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerEntryPoint).authenticationEntryPoint(authenticationHandlerEntryPoint)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
