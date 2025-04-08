package com.example.medjool.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SimpleFilter extends OncePerRequestFilter {


    Logger logger = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Incoming request: {}Type of request:{}", request.getRequestURI(), request.getMethod());

        // IMPORTANT: Continue the filter chain
        filterChain.doFilter(request, response);

        // You can also log the response if needed
        logger.info("Outgoing response with status: {}", response.getStatus());
    }
}
