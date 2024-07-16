package com.example.main.Repository;

import com.example.main.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByVkId(Long vkId);
    User findAllById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByVkId(Long vkId);
}
