package com.example.main.domain.DTO;

import lombok.Data;

@Data
public class PayloadVK {
    int auth;
    String token;
    int ttl;
    String type;
    String uuid;
}
