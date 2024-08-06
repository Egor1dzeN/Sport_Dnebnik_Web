package com.example.main.domain.enums;

import lombok.Getter;

@Getter
public enum TypeTraining {
    RUN("run", UnitOfMeasurement.Min_Km),
    CYCLE("cycle", UnitOfMeasurement.Km_H),
    SWIM("swim", UnitOfMeasurement.Min_100m),
    STRENGTH("strength", UnitOfMeasurement.Empty),
    ROLLERSKI_SKATE("rollerski_skate", UnitOfMeasurement.Km_H),
    ROLLERSKI_CLASSIC("rollerski_classic", UnitOfMeasurement.Km_H),
    SKI_SKATE("ski_skate", UnitOfMeasurement.Km_H),
    SKI_CLASSIC("ski_classic", UnitOfMeasurement.Km_H),
    WALK("walk", UnitOfMeasurement.Min_Km),
    ROW("row", UnitOfMeasurement.Min_Km),
    BASIC_TRAINING("basic_train", UnitOfMeasurement.Empty),
    UNKNOWN("", UnitOfMeasurement.Empty);


    private final String type;
    private final UnitOfMeasurement unitOfMeas;

    TypeTraining(String type, UnitOfMeasurement unitOfMeas) {
        this.type = type;
        this.unitOfMeas = unitOfMeas;
    }
}
