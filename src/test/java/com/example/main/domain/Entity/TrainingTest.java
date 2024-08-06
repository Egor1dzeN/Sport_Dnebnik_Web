package com.example.main.domain.Entity;

import com.example.main.domain.enums.TypeTraining;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {
    private static Training training1;

    @BeforeAll
    static void training1Init() {

    }

    @Test
    void setPaceTest_Km_H1() {
        LocalTime lt = LocalTime.of(1, 0, 0);
        Training training = new Training(TypeTraining.CYCLE, 25.0, lt);
        assertEquals(training.getPace(), "25.0");

    }
    @Test
    void setPaceTest_Km_H2() {
        LocalTime lt = LocalTime.of(1, 30, 0);
        Training training = new Training(TypeTraining.CYCLE, 30.0, lt);
        assertEquals(training.getPace(), "20.0");

    }
    @Test
    void setPaceTest_Km_H3() {
        LocalTime lt = LocalTime.of(0, 45, 0);
        Training training = new Training(TypeTraining.CYCLE, 60, lt);
        assertEquals(training.getPace(), "80.0");
    }
    @Test
    void setPaceTest_Min_km1() {
        LocalTime lt = LocalTime.of(1, 0, 0);
        Training training = new Training(TypeTraining.RUN, 10, lt);
        assertEquals(training.getPace(), "6.00");
    }
    @Test
    void setPaceTest_Min_km2() {
        LocalTime lt = LocalTime.of(1, 0, 0);
        Training training = new Training(TypeTraining.RUN, 16, lt);
        assertEquals(training.getPace(), "3.75");
    }
}