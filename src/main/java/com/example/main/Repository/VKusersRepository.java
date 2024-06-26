package com.example.main.Repository;

import com.example.main.Entity.VKusers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VKusersRepository extends JpaRepository<VKusers, Long> {
    boolean existsByAccessToken(String accessToken);
}
