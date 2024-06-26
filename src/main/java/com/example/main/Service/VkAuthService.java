package com.example.main.Service;

import com.example.main.Entity.VKusers;
import com.example.main.Object.PayloadVK;
import com.example.main.Repository.VKusersRepository;
import com.example.main.VK.AnswerVK_accessToken;
import com.example.main.enums.VkAuth;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VkAuthService {
    @Value("${vk.token}")
    private String service_token;
    private final VKusersRepository vKusersRepository;
    private final Gson gson;
    public VkAuth authWithVK(String payloadStr){
        Gson gson = new Gson();
        PayloadVK payload = gson.fromJson(payloadStr, PayloadVK.class);
        System.out.println(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://api.vk.com/method/auth.exchangeSilentAuthToken";
        String query = "v=5.131&token="+payload.getToken()+"&access_token="+service_token+"&uuid="+payload.getUuid();
        System.out.println(url+" !! "+query);
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);
        System.out.println(requestEntity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
//            System.out.println(response.getBody());
            AnswerVK_accessToken answerVKAccessToken = gson.fromJson(response.getBody(), AnswerVK_accessToken.class);
            System.out.println(answerVKAccessToken);
            getUserFromVk(answerVKAccessToken.getAccessToken(), answerVKAccessToken.getUserId());
        } else {
            System.out.println("Failed to send data: " + response.getStatusCode());
        }
        return VkAuth.CREATE;
    }
    private VKusers getUserFromVk(String accessToken, Long userId){
        String url = "https://api.vk.com/method/users.get";
        String query="v=5.199&access_token="+accessToken+"&user_ids="+userId;
        System.out.println(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println(response.getBody());
        Response response1 = gson.fromJson(response.getBody(), Response.class);
        System.out.println(response1);
        return null;
    }
    private boolean existVkAccount(String accessToken){
        return vKusersRepository.existsByAccessToken(accessToken);
    }
}
@Data
class Response{
    List<VkUser> response;
}
@Data
class VkUser{
    private long id;
    private String first_name;
    private String last_name;
}
