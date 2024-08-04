package com.example.main.Repository;

import com.example.main.domain.Entity.User;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Data
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userSaveTest() {
        userRepository.save(new User("egorka"));
    }
}
