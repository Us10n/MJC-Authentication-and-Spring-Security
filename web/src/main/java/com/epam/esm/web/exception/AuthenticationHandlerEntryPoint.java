package com.epam.esm.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationHandlerEntryPoint implements AuthenticationEntryPoint {

    private static final String ENCODING = "UTF-8";
    private static final String VERSION = " custom";
    private static final String UNAUTHORIZED_MESSAGE = "error.unauthorized";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationHandlerEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Map<String, String> errorResponse = new HashMap<>();
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String message = messageSource.getMessage(UNAUTHORIZED_MESSAGE, null, request.getLocale());
        errorResponse.put(ERROR_MESSAGE, message);
        errorResponse.put(ERROR_CODE, HttpStatus.UNAUTHORIZED.value() + VERSION);
        response.getWriter().write(errorResponse.toString());
    }
}
