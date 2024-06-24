package com.example.main.Service;


import com.example.main.Object.Payload;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String method(){
        return "main";
    }
    @GetMapping("/a")
    public String method2(
//            @RequestParam(value = "payload") String payloadStr, @RequestParam String state,
            Principal principal){
        Gson gson = new Gson();
//        Payload payload = gson.fromJson(payloadStr, Payload.class);
//        System.out.println(payload);
        System.out.println(principal.getName());
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
