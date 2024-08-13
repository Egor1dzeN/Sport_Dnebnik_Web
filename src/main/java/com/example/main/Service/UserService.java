package com.example.main.Service;

import com.example.main.MyException.UserAlreadyExistException;
import com.example.main.MyException.UserEmailException;
import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.User;
import com.example.main.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(UserService.class);

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
//        System.out.println("New user2 = "+user);
        if (userRepository.existsByUsername(user.getUsername())) {
//            logger.info("Username - {}", user.getUsername());
            throw new UserAlreadyExistException("User with this username is already exist :(");
        }
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserEmailException("User with this email is already exist :(");
        return save(user);
    }

    public User getUserByUsername(String username) {
//        System.out.println(username);
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findAllById(id);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);

    }
}
