package com.example.demo.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.domain.User;

class UserAdapterOutTest {

    @Mock
    private UserRepository userRepository;

    private UserAdapterOut adapterOut;

    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
        adapterOut = new UserAdapterOut(userRepository);
    }

    @Test
    void testLoadReturnsUser() {
        // Preparar entrada
        User input = new User();
        input.setUsername("test");
        input.setPassw("123");

        // Preparar DTO simulado del repositorio
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setUserToken("abc123");
        dto.setRolesStr("ROLE_USER");

        // Ajustar mock para que coincida con la firma exacta del repositorio
        when(userRepository.loginAndRegister(
        		any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(),
                any(), any(), any(), any(), anyBoolean(), anyBoolean()
        )).thenReturn(dto);

        // Llamar al método
        User result = adapterOut.load(input, "session", true, true);

        // Comprobaciones
        assertNotNull(result, "El resultado no debe ser null");
        assertEquals("test", result.getUsername());
        assertEquals("abc123", result.getUserToken());
        assertEquals(1, result.getRoles().size());
        assertEquals("ROLE_USER", result.getRoles().get(0));
    }

    @Test
    void testDeleteUserToken() {
        // Llamada al método, no debe lanzar excepción
        assertDoesNotThrow(() -> adapterOut.deleteUserToken("abc123"));

        // Verificar que se llamó al repositorio
        verify(userRepository).deleteUserToken("abc123");
    }
}