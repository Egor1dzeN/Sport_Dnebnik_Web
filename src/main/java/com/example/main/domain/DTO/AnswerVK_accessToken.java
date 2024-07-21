package com.example.main.domain.DTO;

import lombok.Data;

@Data
public class AnswerVK_accessToken {
    private Response response;

    public String getAccessToken() {
        return response.getAccess_token();
    }

    public Long getUserId() {
        return response.getUser_id();
    }

}
