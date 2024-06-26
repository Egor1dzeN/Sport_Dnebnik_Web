package com.example.main.Controller;

import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.SignInRequest;
import com.example.main.Object.SignUpRequest;
import com.example.main.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class AuthController {
    private final AuthService authService;



    @GetMapping("/test3")
    public String method() {
        return "Hello, World";
    }

}
