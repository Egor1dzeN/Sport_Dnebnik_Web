package com.example.main.domain.DTO;

import com.example.main.domain.enums.TypeTraining;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TrainingWithUsername {
    private Long id;
    private double distance;
    private String comment;
    private LocalTime duration;
    private int heartRate;
    private TypeTraining typeTraining;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    private String username;

    public TrainingWithUsername(Long id, double distance, String comment, LocalTime duration,
                                int heartRate, TypeTraining typeTraining, LocalDateTime startTime,
                                String username) {
        this.id = id;
        this.distance = distance;
        this.comment = comment;
        this.duration = duration;
        this.heartRate = heartRate;
        this.typeTraining = typeTraining;
        this.startTime = startTime;
        this.username = username;
    }
}
