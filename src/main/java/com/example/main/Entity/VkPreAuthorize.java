package com.example.main.Entity;

import com.example.main.Object.VkUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VkPreAuthorize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long vkUserId;
    String first_name;
    String last_name;

    public VkPreAuthorize(Long vkUserId, String first_name, String second_name) {
        this.vkUserId = vkUserId;
        this.first_name = first_name;
        this.last_name = second_name;
    }
    public VkPreAuthorize(VkUser vkUser){
        this.vkUserId = vkUser.getId();
        this.first_name = vkUser.getFirst_name();
        this.last_name = vkUser.getLast_name();
    }
}
