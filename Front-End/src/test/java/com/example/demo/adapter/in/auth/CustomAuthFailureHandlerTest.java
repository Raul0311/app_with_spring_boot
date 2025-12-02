package com.example.demo.adapter.in.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.ServletException;

class CustomAuthFailureHandlerTest {

    private CustomAuthFailureHandler handler;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        handler = new CustomAuthFailureHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testAccountCreatedExceptionRedirectsRegistered() throws IOException, ServletException {
        AuthenticationException ex = new CustomAuthenticationProvider.AccountCreatedException("Created");

        handler.onAuthenticationFailure(request, response, ex);

        assertEquals("/auth?registered=true", response.getRedirectedUrl());
    }

    @Test
    void testUserExistsExceptionRedirectsRegisterError() throws IOException, ServletException {
        AuthenticationException ex = new CustomAuthenticationProvider.UserExistsException("Exists");

        handler.onAuthenticationFailure(request, response, ex);

        assertEquals("/auth/register?error=userexists", response.getRedirectedUrl());
    }

    @Test
    void testOtherExceptionRedirectsErrorTrue() throws IOException, ServletException {
        AuthenticationException ex = new AuthenticationException("Other") {};

        handler.onAuthenticationFailure(request, response, ex);

        assertEquals("/auth?error=true", response.getRedirectedUrl());
    }
}
