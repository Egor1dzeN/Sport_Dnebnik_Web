package com.example.main.Service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateRandomStringService {

    public String generateRandomString(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
