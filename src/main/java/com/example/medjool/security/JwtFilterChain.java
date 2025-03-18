package com.example.medjool.security;

import com.example.medjool.model.Manager;
import com.example.medjool.repository.ManagerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;
    private final ManagerRepository managerRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtilities.getToken(request);

        if (token!=null && jwtUtilities.validateToken(token)) {
            // Extract the username
            String email = jwtUtilities.extractUserName(token);
            // Load user details
            Manager manager = managerRepository.findByEmail(email);
            if (manager != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(manager.
                                getUsername() ,null , manager.getAuthorities());
                log.info("authenticated user with email :{}", email);

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
