package com.example.demo.application.usercase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.application.usercases.UserUsecase;

class UserUsecaseTest {

    private UserUsecase userUsecase;

    @BeforeEach
    void setUp() {
        userUsecase = new UserUsecase();
    }

    @Test
    void testLoadRedirectPrivateHome() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);

        String result = userUsecase.load(auth);
        assertEquals("redirect:/private/home", result);
    }

    @Test
    void testLoadRegisterPath() {
        // Simula request a /auth/register
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/auth/register");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String result = userUsecase.load(null);
        assertEquals("register", result);
    }

    @Test
    void testLoadAuthDefault() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/auth");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String result = userUsecase.load(null);
        assertEquals("auth", result);
    }
}
