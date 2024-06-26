package com.example.main.VK;

import lombok.Data;

@Data
public class AnswerVK_accessToken {
    private Response response;
    public String getAccessToken(){
        return response.getAccess_token();
    }
    public Long getUserId(){
        return response.getUser_id();
    }

}
@Data
class Response{
    private String access_token;
    private Long user_id;
}
