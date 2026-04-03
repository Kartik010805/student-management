package com.sms.student_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // APIs with fetch → disable CSRF
                .csrf(csrf -> csrf.disable())

                // enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // routes
                .authorizeHttpRequests(auth -> auth
                        // allow preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // allow static pages
                        .requestMatchers(
                                "/", "/index.html", "/students.html", "/attendance.html",
                                "/css/**", "/js/**"
                        ).permitAll()

                        // everything else needs auth
                        .anyRequest().authenticated()
                )

                // ✅ ENABLE basic auth (so your JS header works)
                .httpBasic(httpBasic -> {})

                // ❌ remove browser popup behavior
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(401);
                        })
                );

        return http.build();
    }

    // ✅ GLOBAL CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*")); // for now
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}