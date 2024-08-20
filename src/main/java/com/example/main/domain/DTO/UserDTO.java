package com.example.main.domain.DTO;

import com.example.main.domain.Entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String name;
    private String surname;
    private String aboutMe;
    private FriendStatusDTO friendStatusDTO;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.aboutMe = user.getAboutMe();
    }
}
