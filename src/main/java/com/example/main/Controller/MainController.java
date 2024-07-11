package com.example.main.Controller;

import com.example.main.Entity.Role;
import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.SignInRequest;
import com.example.main.Object.SignUpRequest;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final AuthService authService;
    private final CookieService cookieService;
    @GetMapping("/login")
    public String method3(Model model){
        model.addAttribute("key_vk", "123451");
        return "login";
    }


    @GetMapping("/sign-up")
    public String signUp(){
        return "signUp";
    }
    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute SignUpRequest request) {
        System.out.println(request);
        JwtTokenResponse tokenResponse = authService.signUp(request);
        if (tokenResponse == null)
            return "redirect:/sign-up";
        return "redirect:/login";
    }

}

