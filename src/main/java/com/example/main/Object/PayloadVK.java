package com.example.main.Object;

import lombok.Data;

@Data
public class PayloadVK {
    int auth;
    String token;
    int ttl;
    String type;
    String uuid;
}
