package com.example.demo.adapter.in.config;

import com.example.demo.adapter.out.persistence.UserAdapterOut;
import com.example.demo.adapter.in.auth.CustomAuthFailureHandler;
import com.example.demo.adapter.in.auth.CustomAuthenticationProvider;
import com.example.demo.adapter.in.auth.CustomHttpSessionEventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * The Class SecurityConfig.
 */
@Configuration
public class SecurityConfig {

    /** The custom auth provider. */
    private final CustomAuthenticationProvider customAuthProvider;

    /**
     * Instantiates a new security config.
     *
     * @param customAuthProvider the custom auth provider
     */
    public SecurityConfig(CustomAuthenticationProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }
    
    /**
     * Authentication manager.
     *
     * @param authConfig the auth config
     * @return the authentication manager
     */
    // AuthenticationManager personalizado
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        return customAuthProvider::authenticate;
    }

    /**
     * Http session event publisher.
     *
     * @param userService the user service
     * @return the http session event publisher
     */
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher(UserAdapterOut userService) {
        return new CustomHttpSessionEventPublisher(userService);
    }

    /**
     * Security filter chain.
     *
     * @param http the http
     * @param failureHandler the failure handler
     * @return the security filter chain
     */
    // Configuración de seguridad
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthFailureHandler failureHandler) {
        http
        	.authorizeHttpRequests(auth -> auth
    			.requestMatchers("/private/**").hasAnyRole("USER", "ADMIN")
    			// "/auth", "/register", "/auth/register", "/static/**"
        	    .requestMatchers("/**").permitAll()
            )
            // Configuración del login
            .formLogin(form -> form
                .loginPage("/auth")                 // GET /login muestra el formulario
                .loginProcessingUrl("/auth")        // POST /login procesa login
                .defaultSuccessUrl("/private/home", true)    // redirige a /home tras login exitoso
                .failureHandler(failureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            // Configuración del logout
            .logout(logout -> logout
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/auth?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .headers(headers -> headers
        		.frameOptions(FrameOptionsConfig::sameOrigin))
            // CSRF por defecto
            .csrf(Customizer.withDefaults());

        return http.build();
    }
}
