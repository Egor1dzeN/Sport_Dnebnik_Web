package com.example.main.Repository;

import com.example.main.Entity.Likes;
import com.example.main.Object.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    long countByTrainingId(Long id);
}
