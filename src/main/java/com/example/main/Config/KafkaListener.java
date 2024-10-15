package com.example.main.Config;

import com.example.main.domain.DTO.UserDTO;
import com.example.main.domain.Entity.User;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final Gson gson;

    @org.springframework.kafka.annotation.KafkaListener(topics = KafkaConfig.NEW_TRAINING_TOPIC, groupId = "group_id")
    public void consumer(String userJson) {
        System.out.println();
        System.err.println("HELLO");
//        try {
            UserDTO user = gson.fromJson(userJson, UserDTO.class);
//            System.out.println(user);
        messagingTemplate.convertAndSend("/topic/messages", user);
//        } finally {
        System.out.println(userJson);
//        }


        System.out.println();
    }

}
