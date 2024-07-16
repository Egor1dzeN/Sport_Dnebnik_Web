package com.example.main.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;
    private LocalDateTime localDateTime;

    public Comment(Training training, User user, String text) {
        this.training = training;
        this.user = user;
        this.text = text;
        localDateTime = LocalDateTime.now();
    }

    public Comment() {

    }
}
