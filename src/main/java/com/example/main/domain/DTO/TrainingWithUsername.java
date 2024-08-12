package com.example.main.domain.DTO;

import com.example.main.domain.Entity.Training;
import com.example.main.domain.enums.TypeTraining;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;

@Data
public class TrainingWithUsername {
    private String username;
    private TypeTraining typeTraining;
    private double distance;
    private LocalDateTime startTime;
    private LocalTime duration;
    private String comment;
    @JsonProperty(value = "avg_heart_rate")
    private int avgHeartRate;
    private String pace;
    @JsonProperty(value = "unit_of_meas")
    private String unitOfMeas;
    public TrainingWithUsername(String username,
                                TypeTraining typeTraining,
                                double distance,
                                LocalDateTime startTime,
                                LocalTime duration,
                                String comment,
                                int avgHeartRate) {

        this.username = username;
        this.typeTraining = typeTraining;
        this.distance = distance;
        this.startTime = startTime;
        this.duration = duration;
        this.comment = comment;
        this.avgHeartRate = avgHeartRate;

    }
    public TrainingWithUsername(Training training){
        this.username = Optional.of(training.getUser().getUsername()).orElse("user_uknown");
        this.typeTraining = training.getTypeTraining();
        this.distance = training.getDistance();
        this.startTime = training.getStartTime();
        this.duration = training.getDuration();
        this.comment = training.getComment();
        this.avgHeartRate = training.getAvgHeartRate();
        training.setPace();
        this.pace = training.getPace();
        this.unitOfMeas = training.getUnitOfMeas();
    }
    @PostConstruct
    public void setPace() {
        this.unitOfMeas = typeTraining.getUnitOfMeas().toString();
        switch (typeTraining.getUnitOfMeas()) {
            case Km_H -> {
                double hour = duration.getHour() + duration.getMinute() / 60.0
                        + duration.getSecond() / 3600.0;
                double pace = distance / hour;
                this.pace = String.format(Locale.US, "%.1f", pace);
            }
            case Min_Km -> {
                double minute = duration.getHour() * 60 + duration.getMinute()
                        + duration.getSecond() / 60.0;
                double pace = minute / distance;
                this.pace = String.format(Locale.US, "%.2f", pace);
            }
            case Min_100m -> {
                double minute = duration.getHour() * 60 + duration.getMinute()
                        + duration.getSecond() / 60.0;
                double pace = minute / (distance * 100);
                this.pace = String.format(Locale.US, "%.2f", pace);
            }
            default -> this.pace = "0.0";
        }


    }
}
