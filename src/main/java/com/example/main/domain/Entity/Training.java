package com.example.main.domain.Entity;

import com.example.main.domain.enums.TypeTraining;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Training {
    @Id
    @GeneratedValue
    private Long id;
    @Transient
    @JsonProperty("training_type")
    private String typeTrainingStr;
    private TypeTraining typeTraining;
    private double distance;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    private LocalTime duration;
    private String comment;
    private int heartRate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Training() {
    }

    public Training(String typeTrainingStr, double distance, LocalDateTime startTime, LocalTime duration, String comment, int heartRate) {
        setTypeTrainingStr(typeTrainingStr);
        this.typeTrainingStr = typeTrainingStr;
        this.distance = distance;
        this.startTime = startTime;
        this.duration = duration;
        this.comment = comment;
        this.heartRate = heartRate;

    }

    public void setTypeTrainingStr(String typeTrainingStr){
        this.typeTrainingStr = typeTrainingStr;
        TypeTraining[] arrayTypeTraining = TypeTraining.values();
        for (TypeTraining typeTraining : arrayTypeTraining){
            if(typeTraining.getType().equals(typeTrainingStr)){
                this.typeTraining = typeTraining;
                return;
            }
        }
        this.typeTraining = TypeTraining.UNKNOWN;
    }
}
