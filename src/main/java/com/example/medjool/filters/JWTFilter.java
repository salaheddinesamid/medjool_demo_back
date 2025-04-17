package com.example.medjool.filters;

import com.example.medjool.jwt.JwtUtilities;
import com.example.medjool.services.implementation.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getLocalAddr());

        // Extract the token from the HTTP headers
        String token = jwtUtil.getToken(request);
        System.out.println(request.getHeader("Authorization"));
        if (token != null && jwtUtil.validateToken(token)) {
            // Extract the username
            String email = jwtUtil.extractUserName(token);
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.
                                getUsername(), null, userDetails.getAuthorities());
                log.info("authenticated user with email :{}", email);

                // Set the authentication context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
