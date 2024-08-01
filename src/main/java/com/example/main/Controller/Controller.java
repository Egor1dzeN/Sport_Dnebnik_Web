package com.example.main.Controller;


import com.example.main.domain.Entity.User;
import com.example.main.domain.Entity.VkUser;
import com.example.main.domain.DTO.JwtTokenResponse;
import com.example.main.Repository.UserRepository;
import com.example.main.Repository.VkUserRepository;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import com.example.main.Service.JwtService;
import com.example.main.Service.VkAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final CookieService cookieService;
    private final VkUserRepository vkUserRepository;
    private final JwtService jwtService;

    @GetMapping("/")
    public String method(Model model, Principal principal) {
        if (principal == null)
            return "index_without_login";
        model.addAttribute("key_vk", "1234561");
        return "index";
    }




    @GetMapping("/test1")
    public String forAll2(Principal principal) {
        System.out.println(principal);
        return "test";
    }

    public String addUsername() {
        return "addUsername";
    }
}

