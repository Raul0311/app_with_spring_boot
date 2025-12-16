package com.example.demo.adapter.in.auth;

import jakarta.servlet.http.HttpSessionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import com.example.demo.adapter.out.persistence.UserAdapterOut;

import java.util.Optional;

/**
 * The Class CustomHttpSessionEventPublisher.
 */
@Slf4j // Logger de Lombok (Reemplaza System.out/err)
public class CustomHttpSessionEventPublisher extends HttpSessionEventPublisher {

    private final UserAdapterOut userService;

    public CustomHttpSessionEventPublisher(UserAdapterOut userService) {
        this.userService = userService;
    }

    /**
     * Session destroyed.
     * Maneja la limpieza del token del usuario cuando la sesión expira o se cierra.
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // Usamos Optional para evitar los NullPointerException que marca SonarQube
        extractSessionId(event).ifPresent(sessionId -> {
            try {
                userService.deleteUserToken(sessionId);
                log.info("Sesión destruida correctamente! ID: {}", sessionId);
            } catch (Exception e) {
                log.error("No se pudo eliminar el token de sesión para ID {}: {}", sessionId, e.getMessage());
            }
        });

        super.sessionDestroyed(event);
    }

    /**
     * Método auxiliar para navegar el contexto de seguridad de forma segura.
     */
    private Optional<String> extractSessionId(HttpSessionEvent event) {
        return Optional.ofNullable(event.getSession().getAttribute("SPRING_SECURITY_CONTEXT"))
                .filter(SecurityContext.class::isInstance)
                .map(SecurityContext.class::cast)
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getDetails)
                .filter(WebAuthenticationDetails.class::isInstance)
                .map(WebAuthenticationDetails.class::cast)
                .map(WebAuthenticationDetails::getSessionId);
    }
}