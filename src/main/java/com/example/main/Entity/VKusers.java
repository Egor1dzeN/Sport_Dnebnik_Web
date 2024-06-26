package com.example.main.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vk_users")
@Data
public class VKusers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    String accessToken;
    String user_id;
    Long phoneValidate;
    String email;
}
