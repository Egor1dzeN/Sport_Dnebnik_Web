package com.example.main.Repository;

import com.example.main.Entity.VkPreAuthorize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VkPreAuthorizeRepository extends JpaRepository<VkPreAuthorize, Long> {
    boolean existsByVkUserId(long vkUserId);
}
