package com.example.main.domain.enums;

import lombok.Getter;

@Getter
public enum TypeTraining {
    RUN("run"),
    CYCLE("cycle"),
    SWIM("swim"),
    STRENGTH("strength"),
    ROLLERSKI_SKATE("rollerski_skate"),
    ROLLERSKI_CLASSIC("rollerski_classic"),
    SKI_SKATE("ski_skate"),
    SKI_CLASSIC("ski_classic"),
    UNKNOWN("");


    private String type;

    TypeTraining(String type) {
        this.type = type;
    }
}
