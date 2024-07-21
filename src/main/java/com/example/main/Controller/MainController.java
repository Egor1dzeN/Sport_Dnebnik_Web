package com.example.main.Controller;

import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final AuthService authService;
    private final CookieService cookieService;


}

