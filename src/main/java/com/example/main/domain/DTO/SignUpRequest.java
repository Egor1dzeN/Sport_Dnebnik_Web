package com.example.main.domain.DTO;

import lombok.Data;

@Data
public class SignUpRequest {
    String username;
    String password;
    String email;
    String name;
    String surname;

}
