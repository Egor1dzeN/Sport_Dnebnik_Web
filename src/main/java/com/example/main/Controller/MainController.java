package com.example.main.Controller;

import com.example.main.Object.JwtTokenResponse;
import com.example.main.Object.SignInRequest;
import com.example.main.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final AuthService authService;
    @GetMapping("/login")
    public String method3(){
        return "login";
    }
    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute SignInRequest signInRequest, HttpServletResponse response){
//        System.out.println("hdjahsj");
        JwtTokenResponse tokenResponse = authService.signIn(signInRequest);
        if (tokenResponse != null){
            Cookie cookie = new Cookie("auth", tokenResponse.getToken());
            response.addCookie(cookie);
//            System.out.println("hdjahsj");
            return "redirect:/";
        }
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("auth", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }

}

