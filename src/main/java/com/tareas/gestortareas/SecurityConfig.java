package com.tareas.gestortareas;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF para una API
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso sin autenticación a las rutas de autenticación
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        // Proteger las demás rutas, exigiendo autenticación
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}

