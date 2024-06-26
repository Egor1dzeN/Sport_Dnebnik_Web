package com.example.main.Controller;


import com.example.main.Object.PayloadVK;
import com.example.main.Service.VkAuthService;
import com.example.main.VK.AnswerVK_accessToken;
import com.example.main.enums.VkAuth;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {
    private final VkAuthService vkAuthService;

    @GetMapping("/")
    public String method(Model model){
        model.addAttribute("key_vk","1234561");
        return "index";
    }
    @GetMapping("/a")
    public String method2(
            @RequestParam(value = "payload") String payloadStr, @RequestParam String state,
            Principal principal){
        VkAuth vkAuth = vkAuthService.authWithVK(payloadStr);
        return "index2";
    }
    @GetMapping("/test")
    public String forAll(){
        return "test";
    }
    @GetMapping("/test1")
    public String forAll2(){
        return "test";
    }
}
