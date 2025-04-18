package com.example.medjool.config;

import com.example.medjool.filters.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTFilter JWTFilter;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

                http.csrf()
                        .disable()
                        .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests.requestMatchers("/api/order/**",
                                        "/api/auth/**",
                                        "/api/stock/**",
                                        "/api/overview/**",
                                        "/api/configuration/**",
                                        "/api/alert/**",
                                        "/api/notification/**",
                                        "/api/shipment/**"
                                                ).permitAll()
                        )

                        .addFilterBefore(JWTFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
