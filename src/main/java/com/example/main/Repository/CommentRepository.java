package com.example.main.Repository;

import com.example.main.domain.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM Comment as c where c.training_id=:training_id LIMIT :limit OFFSET :offset ", nativeQuery = true)
    List<Comment> findByTrainingId(@Param("training_id") Long trainingId,@Param("limit") int limit,@Param("offset") int offset);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO comment (local_date_time, training_id, user_id, text) VALUES (:create_time, :training_id, :user_id, :text)", nativeQuery = true)
    void saveComment(@Param("create_time")LocalDateTime createTime, @Param("training_id") Long trainingId, @Param("user_id") Long userId, @Param("text")String comment);
}
