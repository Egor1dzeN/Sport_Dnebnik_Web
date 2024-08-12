package com.example.main.Service;

import com.example.main.domain.DTO.JwtTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    private final Logger logger = LogManager.getLogger(CookieService.class);
    public static final String TOKEN_AUTH_STR = "Session_id";
    public void addCookie(HttpServletResponse response, JwtTokenResponse tokenResponse) {
        ResponseCookie cookie = ResponseCookie.from(TOKEN_AUTH_STR, tokenResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_AUTH_STR, null);
        cookie.setMaxAge(0);
        logger.info("Cookie {}", "deleted");
        response.addCookie(cookie);
    }
}
