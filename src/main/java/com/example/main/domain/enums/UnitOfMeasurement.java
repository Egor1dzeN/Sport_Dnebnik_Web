package com.example.main.domain.enums;



public enum UnitOfMeasurement {
    Km_H("км/ч"),
    Min_Km("мин/км"),
    Min_100m("мин/100m"),
    Empty("");
    private final String str;
    UnitOfMeasurement(String str){
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
