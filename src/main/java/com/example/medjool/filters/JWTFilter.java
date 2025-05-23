package com.example.medjool.filters;

import com.example.medjool.exception.TokenExpiredException;
import com.example.medjool.exception.UserAccountLockedException;
import com.example.medjool.exception.UserNotFoundException;
import com.example.medjool.jwt.JwtUtilities;
import com.example.medjool.services.implementation.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            System.out.println(request.getLocalAddr());

            // Extract the token from the HTTP headers
            String token = jwtUtil.getToken(request);
            System.out.println(token);
            if (token != null && jwtUtil.validateToken(token)) {
                // Extract the username
                String email = jwtUtil.extractUserName(token);
                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (userDetails != null && userDetails.isAccountNonLocked()) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails.
                                    getUsername(), null, userDetails.getAuthorities());
                    log.info("authenticated user with email :{}", email);

                    // Set the authentication context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                else if(userDetails != null && !userDetails.isAccountNonLocked()) {
                    throw new UserAccountLockedException("User account is locked");
                }
                else {
                    throw new UserNotFoundException();

                }
            }

        }catch (ExpiredJwtException ex) {
            // Specifically handle expired tokens
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Map<String, Object> body = new HashMap<>();
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            body.put("error", "Unauthorized");
            body.put("message", "JWT expired");
            body.put("path", request.getRequestURI());

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
