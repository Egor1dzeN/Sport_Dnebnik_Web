package com.example.main.Repository;

import com.example.main.domain.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTrainingId(Long id);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO comment(training_id, user_id, text, local_date_time) VALUES (:training_id, :user_id, :text, :local_date_time)", nativeQuery = true)
//    void insertComment(@Param("training_id")Long trainingId, @Param("user_id")Long userId, @Param("text") String text, @Param("local_date_time")LocalDateTime time);
}
