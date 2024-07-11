package com.example.main.Service;

import com.example.main.Object.JwtTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public void addCookie(HttpServletResponse response, JwtTokenResponse tokenResponse) {
        ResponseCookie cookie = ResponseCookie.from("auth", tokenResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
