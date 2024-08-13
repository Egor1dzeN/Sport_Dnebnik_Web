package com.example.main.Repository;

import com.example.main.domain.Entity.Likes;
import com.example.main.domain.DTO.LikesId;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
    long countByTrainingId(Long trainingId);
    boolean existsAllByTrainingAndUser(Training training, User user);
    void deleteByTrainingAndUser(Training training, User user);
    Optional<Likes> findByTrainingAndUser(Training training, User user);
}
