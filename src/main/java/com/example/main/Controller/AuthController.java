package com.example.main.Controller;

import com.example.main.Entity.Role;
import com.example.main.Entity.User;
import com.example.main.Entity.VkUser;
import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.SignInRequest;
import com.example.main.Object.SignUpRequest;
import com.example.main.Repository.UserRepository;
import com.example.main.Repository.VkUserRepository;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import com.example.main.Service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Data
public class AuthController {
    private final AuthService authService;
    private final VkUserRepository vkUserRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;

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
    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute SignInRequest signInRequest, HttpServletResponse response){
        JwtTokenResponse tokenResponse = authService.signIn(signInRequest);
        if (tokenResponse != null){
            cookieService.addCookie(response, tokenResponse);
            return "redirect:/";
        }
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Principal principal){
        cookieService.removeCookie(response);
        return "redirect:/login";
    }

}
