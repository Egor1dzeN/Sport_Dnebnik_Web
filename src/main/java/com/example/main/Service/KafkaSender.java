package com.example.main.Service;

import com.example.main.Config.KafkaListener;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate kafkaTemplate;
    private final Gson gson;

    public void send(String topic, String data){
        kafkaTemplate.send(topic, data);
    }
    public void send(String topic, Object object){
        String dataObject = gson.toJson(object);
        send(topic, dataObject);
    }

}
