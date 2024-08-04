package com.example.main.Controller;


import com.example.main.Repository.UserRepository;
import com.example.main.Repository.VkUserRepository;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import com.example.main.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final CookieService cookieService;
    private final VkUserRepository vkUserRepository;
    private final JwtService jwtService;

    private final Logger logger = LogManager.getLogger(MainController.class);
    @GetMapping("/")
    public String method(Model model, Principal principal) {
        if (principal == null) {
            logger.info("User not auth");
            return "index_without_login";
        }
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

