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
                // ❌ disable CSRF (needed for API + JS fetch)
                .csrf(csrf -> csrf.disable())

                // ✅ enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // ✅ authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🔥 allow preflight requests (CRITICAL)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // allow static pages
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/students.html",
                                "/attendance.html",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // protect everything else
                        .anyRequest().authenticated()
                )

                // ❌ disable browser popup
                .httpBasic(httpBasic -> httpBasic.disable())

                // ✅ return 401 instead of popup
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> res.setStatus(401))
                );

        return http.build();
    }

    // ✅ GLOBAL CORS CONFIG
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*")); // allow all (for now)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false); // must be false when using "*"

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}