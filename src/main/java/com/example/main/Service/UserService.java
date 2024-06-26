package com.example.main.Service;

import com.example.main.Entity.Role;
import com.example.main.Entity.User;
import com.example.main.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User save(User user){
        return userRepository.save(user);
    }
    public User create(User user){
        if (userRepository.existsByUsername(user.getUsername()))
            return null;
        if (userRepository.existsByEmail(user.getEmail()))
            return null;
        return save(user);
    }
    public User getUserByUsername(String username){
        System.out.println(username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User wasn't find"));
    }
    public UserDetailsService userDetailsService(){
        return this::getUserByUsername;
    }
    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }
    public void getAdmin(){
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);

    }
}
