package com.example.main.Service;

import com.example.main.Entity.VkUser;
import com.example.main.Object.PayloadVK;
import com.example.main.Object.VkUserResponse;
import com.example.main.Repository.VkUserRepository;
import com.example.main.VK.AnswerVK_accessToken;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VkAuthService {
    @Value("${vk.token}")
    private String service_token;
    private final Gson gson = new Gson();
    private final VkUserRepository vkUserRepository;
    private final GenerateRandomStringService generateRandomStringService;

    public VkUser authWithVK(String payloadStr) {
        Gson gson = new Gson();
        PayloadVK payload = gson.fromJson(payloadStr, PayloadVK.class);
        System.out.println(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://api.vk.com/method/auth.exchangeSilentAuthToken";
        String query = "v=5.131&token=" + payload.getToken() + "&access_token=" + service_token + "&uuid=" + payload.getUuid();
        System.out.println(url + " !! " + query);
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);
        System.out.println(requestEntity);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            AnswerVK_accessToken answerVKAccessToken = gson.fromJson(response.getBody(), AnswerVK_accessToken.class);
            System.out.println(answerVKAccessToken);
            VkUserResponse vkUserResponse = getUserFromVk(answerVKAccessToken.getAccessToken(), answerVKAccessToken.getUserId());

            if (vkUserRepository.existsByUserVkId(vkUserResponse.getId())) {
                VkUser vkUser = vkUserRepository.findAllByUserVkId(vkUserResponse.getId());
                return vkUser;
            } else {
                VkUser vkUser = new VkUser(vkUserResponse.getFirst_name(), vkUserResponse.getLast_name(), vkUserResponse.getId());
                String secretKey = generateRandomStringService.generateRandomString(20);
                vkUser.setSecretKey(secretKey);
                vkUserRepository.save(vkUser);
                return vkUser;
            }

        } else {
            System.out.println("Failed to send data: " + response.getStatusCode());
        }
        return null;
    }

    private VkUserResponse getUserFromVk(String accessToken, Long userId) {
        String url = "https://api.vk.com/method/users.get";
        String query = "v=5.199&access_token=" + accessToken + "&user_ids=" + userId;
        System.out.println(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//        System.out.println(response.getBody());
        Response response1 = gson.fromJson(response.getBody(), Response.class);
        System.out.println(response1);
        return response1.getResponse().getFirst();
    }
}

@Data
class Response {
    List<VkUserResponse> response;
}

