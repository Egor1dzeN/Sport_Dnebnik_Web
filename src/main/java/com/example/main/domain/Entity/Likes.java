package com.example.main.domain.Entity;

import jakarta.persistence.*;
// ToDo: add likes for training
@Entity
public class Likes {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
//    private Arr
}
