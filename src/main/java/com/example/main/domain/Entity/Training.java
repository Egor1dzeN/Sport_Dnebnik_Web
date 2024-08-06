package com.example.main.domain.Entity;

import com.example.main.domain.enums.TypeTraining;
import com.example.main.domain.enums.UnitOfMeasurement;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@Entity
@Data
public class Training {
    @Id
    @GeneratedValue
    private Long id;
    @Transient
    @JsonProperty("training_type")
    private String typeTrainingStr;

    private TypeTraining typeTraining = TypeTraining.UNKNOWN;
    private double distance;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    private LocalTime duration;
    private String comment;
    private int avgHeartRate;
    @Transient
    private String pace = "";
    @Transient
    private String unitOfMeas;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Training() {
    }

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
        }


    }

    public Training(TypeTraining typeTraining, double distance, LocalDateTime startTime, LocalTime duration, String comment, int avgHeartRate) {
        this.typeTraining = typeTraining;
        this.distance = distance;
        this.startTime = startTime;
        this.duration = duration;
        this.comment = comment;
        this.avgHeartRate = avgHeartRate;
        setPace();
    }

    public Training(String typeTrainingStr, double distance, LocalDateTime startTime, LocalTime duration, String comment, int avgHeartRate) {
        setTypeTrainingStr(typeTrainingStr);
        this.typeTrainingStr = typeTrainingStr;
        this.distance = distance;
        this.startTime = startTime;
        this.duration = duration;
        this.comment = comment;
        this.avgHeartRate = avgHeartRate;
        setPace();
    }

    public Training(TypeTraining typeTraining, double distance, LocalTime duration) {
        this.typeTraining = typeTraining;
        this.distance = distance;
        this.duration = duration;
        setPace();
    }

    public void setTypeTrainingStr(String typeTrainingStr) {
        this.typeTrainingStr = typeTrainingStr;
        TypeTraining[] arrayTypeTraining = TypeTraining.values();
        for (TypeTraining typeTraining : arrayTypeTraining) {
            if (typeTraining.getType().equals(typeTrainingStr)) {
                this.typeTraining = typeTraining;
                return;
            }
        }
        this.typeTraining = TypeTraining.UNKNOWN;
    }

}
