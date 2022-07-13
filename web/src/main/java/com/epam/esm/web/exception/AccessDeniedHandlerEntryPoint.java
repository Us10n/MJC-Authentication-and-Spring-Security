package com.epam.esm.web.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessDeniedHandlerEntryPoint implements AccessDeniedHandler {

    private static final String ENCODING = "UTF-8";
    private static final String VERSION = " custom";
    private static final String FORBIDDEN_MESSAGE = "error.forbidden";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private final MessageSource messageSource;

    @Autowired
    public AccessDeniedHandlerEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map<String, String> errorResponse = new HashMap<>();
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        String message = messageSource.getMessage(FORBIDDEN_MESSAGE, null, request.getLocale());
        errorResponse.put(ERROR_MESSAGE, message);
        errorResponse.put(ERROR_CODE, HttpStatus.FORBIDDEN.value() + VERSION);

        response.getWriter().write(String.valueOf(
                new ObjectMapper().writeValueAsString(errorResponse)
        ));
    }
}
