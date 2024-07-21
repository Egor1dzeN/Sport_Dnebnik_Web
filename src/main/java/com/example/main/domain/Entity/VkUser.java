package com.example.main.domain.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VkUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String secretKey;
    private Long userVkId;

    public VkUser(String firstName, String lastName, Long userVkId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userVkId = userVkId;
    }
}
