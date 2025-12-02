package com.example.demo.adapter.in.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.demo.application.ports.in.UserPortIn;

class ControllerAdapterInTest {

    private MockMvc mockMvc;

    @Mock
    private UserPortIn userPortIn;

    @InjectMocks
    private ControllerAdapterIn controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Resolver de vistas para que MockMvc encuentre los templates
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                    .standaloneSetup(controller)
                    .setViewResolvers(viewResolver)
                    .build();
    }

    @Test
    void testRootRedirectsToAuth() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/auth"));
    }

    @Test
    void testAuthPageWithoutParams() throws Exception {
        when(userPortIn.load(null)).thenReturn("auth");

        mockMvc.perform(get("/auth"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth"))
               .andExpect(model().attributeDoesNotExist("error"))
               .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    void testAuthPageWithErrorTrue() throws Exception {
        when(userPortIn.load(null)).thenReturn("auth");

        mockMvc.perform(get("/auth").param("error", "true"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", "Usuario o contraseña incorrectos."));
    }

    @Test
    void testAuthPageWithUserExistsError() throws Exception {
        when(userPortIn.load(null)).thenReturn("auth");

        mockMvc.perform(get("/auth").param("error", "userexists"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", "El nombre de usuario ya existe."));
    }

    @Test
    void testAuthPageWithRegisteredTrue() throws Exception {
        when(userPortIn.load(null)).thenReturn("auth");

        mockMvc.perform(get("/auth").param("registered", "true"))
               .andExpect(status().isOk())
               .andExpect(view().name("auth"))
               .andExpect(model().attributeExists("msg"))
               .andExpect(model().attribute("msg", "La cuenta se ha creado correctamente."));
    }

    @Test
    void testRegisterRedirect() throws Exception {
        mockMvc.perform(get("/register"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/auth/register"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testPrivateHomePage() throws Exception {
        mockMvc.perform(get("/private/home"))
               .andExpect(status().isOk())
               .andExpect(view().name("private/home"));
    }
}