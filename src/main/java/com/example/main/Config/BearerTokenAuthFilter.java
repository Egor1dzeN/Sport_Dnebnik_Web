package com.example.main.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class BearerTokenAuthFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(BearerTokenAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Todo: add auth bearer token in header
        //Todo: logger: print username if auth is ok
        doFilter(request, response, filterChain);
    }
}
