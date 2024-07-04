package com.example.main.Repository;

import com.example.main.Entity.VkUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VkUserRepository extends JpaRepository<VkUser, Long> {
    boolean existsByUserVkId(Long id);
    VkUser findAllByUserVkId(Long userVkId);
    VkUser findBySecretKey(String secretKey);
}
