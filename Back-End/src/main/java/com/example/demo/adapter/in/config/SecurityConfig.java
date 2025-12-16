package com.example.demo.adapter.in.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.application.ports.out.UserPortOut;

/**
 * The Class SecurityConfig.
 */
@Configuration
public class SecurityConfig {
	
	/** The user port out. */
	private final UserPortOut userPortOut;
	
	/**
	 * Instantiates a new security config.
	 *
	 * @param userPortOut the user port out
	 */
	public SecurityConfig(UserPortOut userPortOut) {
        this.userPortOut = userPortOut;
    }
	
	/**
	 * User token filter.
	 *
	 * @return the user token filter
	 */
	@Bean
    public UserTokenFilter userTokenFilter() {
        return new UserTokenFilter(userPortOut);
    }
	
	/**
	 * Cors configuration source.
	 *
	 * @return the cors configuration source
	 */
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Define el Origen permitido (tu frontend en 8080)
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000")); 
        
        // Define los métodos HTTP permitidos (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Define los headers permitidos (incluye el Authorization header que usas)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        
        // Permite enviar cookies o credenciales (si fuera necesario, no lo es para tokens)
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración a todas las rutas de la API
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    /**
     * Security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    // Configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    	http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        http
            // 2. Añadir el filtro ANTES de la validación de usuario/contraseña
            .addFilterBefore(userTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            
            // 3. Configurar Autorización (Ahora podemos restringir accesos)
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (Swagger y Registro)
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs", "/api-docs/**").permitAll()
                .requestMatchers("/register").permitAll()
                
                // Las demás rutas requieren autenticación. 
                // Si el filtro no encuentra el token o falla, responderá 401.
                .anyRequest().authenticated() 
            )
            
            // 4. Desactivar CSRF para APIs sin estado
            .csrf(csrf -> csrf.disable())
            
            // 5. Configurar como API sin estado (recomendado cuando usas tokens)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
