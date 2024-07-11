package com.example.main.Entity;

import com.example.main.enums.TypeTraining;
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
