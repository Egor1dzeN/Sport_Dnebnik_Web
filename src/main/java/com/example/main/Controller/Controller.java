package com.example.main.Controller;


import com.example.main.Entity.Role;
import com.example.main.Entity.User;
import com.example.main.Entity.VkUser;
import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.VkUserResponse;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {
    private final VkAuthService vkAuthService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final CookieService cookieService;
    private final VkUserRepository vkUserRepository;
    private final JwtService jwtService;

    @GetMapping("/")
    public String method(Model model) {
        model.addAttribute("key_vk", "1234561");
        return "index";
    }

    @GetMapping("/vk.auth")
    public String method2(
            @RequestParam(value = "payload") String payloadStr, @RequestParam String state,
            Principal principal, Model model, HttpServletResponse response) {
        VkUser vkUser = vkAuthService.authWithVK(payloadStr);
        System.out.println("VkUSER" + vkUser);
        if (userRepository.existsByVkId(vkUser.getUserVkId())) {
            System.out.println("AUTH VK");
            User user = userRepository.findByVkId(vkUser.getUserVkId());
            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.generateToken(user.getUsername()));
            cookieService.addCookie(response, jwtTokenResponse);
            System.out.println(jwtTokenResponse);
            return "redirect:/";
        }
        model.addAttribute("secretKey", vkUser.getSecretKey());
        return "addUsername";


    }

    @PostMapping("/vk.auth")
    public String authVk(@ModelAttribute PayloadVkAuth payloadVkAuth, HttpServletResponse response) {
        String username = payloadVkAuth.getUsername();
        String secretKey = payloadVkAuth.getSecretKey();
        System.out.println(payloadVkAuth);
        VkUser vkUser = vkUserRepository.findBySecretKey(secretKey);
        System.out.println(vkUser);
        User user = new User(username, vkUser.getFirstName(), vkUser.getLastName(), vkUser.getUserVkId());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        System.out.println(user);
        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.generateToken(username));
        cookieService.addCookie(response, jwtTokenResponse);
        System.out.println(jwtTokenResponse);
        return "redirect:/";
    }

    @GetMapping("/test")
    public String forAll() {
        return "test";
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

@Data
class PayloadVkAuth {
    String username;
    String secretKey;
}
