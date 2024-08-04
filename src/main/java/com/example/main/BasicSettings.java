package com.example.main;


import com.example.main.Repository.UserRepository;
import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.User;
import jakarta.persistence.Column;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Data
public class BasicSettings implements CommandLineRunner {
    @Value(value = "${admin.username}")
    private String adminUsername;
    @Value(value = "${admin.password}")
    private String adminPassword;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(BasicSettings.class);

    @Override
    public void run(String... args)  {
        logger.info("Created admin user");
        if (!userRepository.existsByUsername(adminUsername)) {
            userRepository.save(new User(adminUsername, passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN));
        }
    }
}
