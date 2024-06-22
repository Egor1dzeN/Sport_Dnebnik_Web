package com.example.main;


import com.example.main.Object.Payload;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String method(){
        return "main";
    }
    @GetMapping("/a")
    public String method2(@RequestParam(value = "payload") String payloadStr, @RequestParam String state){
        Gson gson = new Gson();
        Payload payload = gson.fromJson(payloadStr, Payload.class);
        System.out.println(payload);
        return "index2";
    }
}
