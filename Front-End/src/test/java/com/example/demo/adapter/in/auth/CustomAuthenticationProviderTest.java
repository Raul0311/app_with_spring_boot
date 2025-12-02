package com.example.demo.adapter.in.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.application.ports.out.UserPortOut;
import com.example.demo.domain.User;

class CustomAuthenticationProviderTest {

    private CustomAuthenticationProvider provider;

    @Mock
    private UserPortOut userPortOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        provider = new CustomAuthenticationProvider();
        provider.setUserPortOut(userPortOut);
    }

    private void mockRequest(String actionRegister, String actionLogin) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        if (actionRegister != null) request.setParameter("actionRegister", actionRegister);
        if (actionLogin != null) request.setParameter("actionLogin", actionLogin);

        MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testSuccessfulLogin() {
        mockRequest(null, "login"); // Simula login

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "pass");

        User u = new User();
        u.setUsername("user");
        u.setPassw("pass");
        u.setUserToken("token");
        u.setRoles(List.of("USER")); // Roles necesarios para authorities

        when(userPortOut.load(any(), anyString(), eq(false), eq(true))).thenReturn(u);

        Authentication result = provider.authenticate(auth);

        assertNotNull(result);
        assertEquals("user", result.getName());
        assertFalse(result.getAuthorities().isEmpty(), "Authorities should not be empty");
    }

    @Test
    void testBadCredentials() {
        mockRequest(null, "login");

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "wrong");

        when(userPortOut.load(any(), anyString(), eq(false), eq(true))).thenReturn(null);

        assertThrows(BadCredentialsException.class, () -> provider.authenticate(auth));
    }

    @Test
    void testRegisterExistingUserThrowsUserExistsException() {
        mockRequest("register", "login");

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "pass");

        // Simulamos que existe → login devuelve false
        when(userPortOut.load(any(), anyString(), eq(true), eq(true))).thenReturn(null);

        assertThrows(CustomAuthenticationProvider.UserExistsException.class,
                     () -> provider.authenticate(auth));
    }

    @Test
    void testRegisterNewUserAccountCreated() {
        mockRequest("register", null);

        Authentication auth = new UsernamePasswordAuthenticationToken("user", "pass");

        User created = new User();
        created.setUsername("user");
        created.setPassw("pass");

        when(userPortOut.load(any(), anyString(), eq(true), eq(false))).thenReturn(created);

        assertThrows(CustomAuthenticationProvider.AccountCreatedException.class,
                     () -> provider.authenticate(auth));
    }
}
