package com.example.main.Object;

import lombok.Data;

@Data
public class Payload {
    int auth;
    String token;
    int ttl;
    String type;
    String uuid;
}
