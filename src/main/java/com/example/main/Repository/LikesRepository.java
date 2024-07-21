package com.example.main.Repository;

import com.example.main.domain.Entity.Likes;
import com.example.main.domain.DTO.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    long countByTrainingId(Long id);
}
