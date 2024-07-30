package com.example.main.Service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class GenerateRandomStringService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateRandomString(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
