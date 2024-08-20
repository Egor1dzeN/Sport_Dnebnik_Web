package com.example.main.domain.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    private Arr

    public Likes(Training training, User user) {
        this.user = user;
        this.training = training;
    }

    public Likes() {
    }
}
