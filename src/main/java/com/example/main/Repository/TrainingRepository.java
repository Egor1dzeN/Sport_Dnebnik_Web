package com.example.main.Repository;

import com.example.main.Entity.Training;
import com.example.main.Object.TrainingWithUsername;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT new com.example.main.Object.TrainingWithUsername( t.id, t.distance, t.comment, t.duration, t.heartRate, t.typeTraining, t.startTime, t.user.username) FROM Training as t")
    List<TrainingWithUsername> findAllTraining();
    @Query ("SELECT new com.example.main.Object.TrainingWithUsername( t.id, t.distance, t.comment, t.duration, t.heartRate, t.typeTraining, t.startTime, t.user.username) FROM Training as t where t.user.id = :user_id")
    List<TrainingWithUsername> findAllByUserId(@Param("user_id") Long userId);
}
