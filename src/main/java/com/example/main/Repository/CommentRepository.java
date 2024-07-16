package com.example.main.Repository;

import com.example.main.Entity.Comment;
import com.example.main.Entity.Training;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTrainingId(Long id);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO comment(training_id, user_id, text, local_date_time) VALUES (:training_id, :user_id, :text, :local_date_time)", nativeQuery = true)
//    void insertComment(@Param("training_id")Long trainingId, @Param("user_id")Long userId, @Param("text") String text, @Param("local_date_time")LocalDateTime time);
}
