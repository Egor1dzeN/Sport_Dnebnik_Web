package com.example.main.Service;

import com.example.main.Object.JwtTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public void addCookie(HttpServletResponse response, JwtTokenResponse tokenResponse){
        Cookie cookie = new Cookie("auth", tokenResponse.getToken());
        response.addCookie(cookie);
    }
    public void removeCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("auth", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
