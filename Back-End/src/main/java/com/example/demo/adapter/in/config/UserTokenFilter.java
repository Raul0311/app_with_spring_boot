package com.example.demo.adapter.in.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.application.ports.out.UserPortOut;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserTokenFilter extends OncePerRequestFilter {

    private final UserPortOut userPortOut;

    public UserTokenFilter(UserPortOut userPortOut) {
        this.userPortOut = userPortOut;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener parámetros userToken
        String authorizationHeader = request.getHeader("Authorization");

        // Rutas que no requieren token (permitidas en SecurityConfig)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            return;
        }
        
        String userToken = authorizationHeader.substring(7);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {

                // 2. Delegar la validación a la capa de persistencia existente
                Long userId = userPortOut.validateUser(userToken);

                // Si la validación pasa, el token es válido y el usuario existe.
                
                // 3. Crear el objeto de autenticación de Spring Security
                // NOTA: Para este ejemplo, cargamos solo los roles/autoridades.
                // En un sistema real, cargarías el UserDetails con el loadUserByUsername.
                
                // Como ya validamos, podemos considerar el usuario autenticado
                // Asumimos que no necesitamos cargar detalles específicos del usuario (roles/authorities) para este ejemplo simple.
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userId, null, null); // Principal es el userId

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Establecer la autenticación en el SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (NumberFormatException e) {
                // userId no es un Long válido, continuar con la cadena
                filterChain.doFilter(request, response);
                return;
            } catch (ResponseStatusException e) {
                // Validación fallida (Invalid user token o User)
                response.sendError(e.getStatusCode().value(), e.getReason());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}