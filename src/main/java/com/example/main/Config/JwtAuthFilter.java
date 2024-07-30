package com.example.main.Config;

import com.example.main.Service.CookieService;
import com.example.main.Service.JwtService;
import com.example.main.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sound.midi.Soundbank;
import java.io.IOException;

@Component
@Data
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jws = extractAuthToken(request.getCookies());
        if (jws == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var username = jwtService.extractUsername(jws);
//        logger.info("Auth {} ", username);
//        UserDetails userDetails2 = userService.userDetailsService().loadUserByUsername(username);
//        logger.info("UserDetails {}", userDetails2);
        if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if (jwtService.isTokenValid(jws, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
//                System.out.println("AUTH is ok!");
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }

        }
        filterChain.doFilter(request, response);
    }

    public String extractAuthToken(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieService.TOKEN_AUTH_STR)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
