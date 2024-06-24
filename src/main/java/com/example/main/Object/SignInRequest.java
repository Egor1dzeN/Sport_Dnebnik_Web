package com.example.main.Object;

import io.jsonwebtoken.Jwts;
import lombok.Data;

@Data
public class SignInRequest {
    String username;
    String password;
}
