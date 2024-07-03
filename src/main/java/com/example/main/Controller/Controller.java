package com.example.main.Controller;


import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.VkUser;
import com.example.main.Service.AuthService;
import com.example.main.Service.CookieService;
import com.example.main.Service.VkAuthService;
import com.example.main.enums.VkAuth;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {
    private final VkAuthService vkAuthService;
    private final AuthService authService;
    private CookieService cookieService;
    @GetMapping("/")
    public String method(Model model){
        model.addAttribute("key_vk","1234561");
        return "index";
    }
    @GetMapping("/vk.auth")
    public String method2(
            @RequestParam(value = "payload") String payloadStr, @RequestParam String state,
            Principal principal, Model model, HttpServletResponse response){
        VkUser vkUser = vkAuthService.authWithVK(payloadStr);
        JwtTokenResponse token = authService.signInVk(vkUser.getFirst_name());
        cookieService.addCookie(response, token);
        if (vkUser.isCreated()){

            return "redirect:/";
        }
        model.addAttribute("vkUser", vkUser);
        return "addUsername";
    }
    @PostMapping("/vk.auth")
    public void authVk(@RequestParam String username, @RequestParam VkUser vkUser){
        System.out.println(username);
        System.out.println(vkUser);
    }
    @GetMapping("/test")
    public String forAll(){
        return "test";
    }
    @GetMapping("/test1")
    public String forAll2(){
        return "test";
    }
    public String addUsername(){
        return "addUsername";
    }
}
