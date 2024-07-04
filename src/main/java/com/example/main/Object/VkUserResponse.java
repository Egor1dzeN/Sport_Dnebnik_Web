package com.example.main.Object;

import lombok.Data;

@Data
public class VkUserResponse {
    private long id;
    private String first_name;
    private String last_name;
    private boolean isCreated;
}
