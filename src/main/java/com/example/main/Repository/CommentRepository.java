package com.example.main.Repository;

import com.example.main.domain.DTO.CommentDTO;
import com.example.main.domain.Entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM Comment as c where c.training_id=:training_id", nativeQuery = true)
    List<Comment> findByTrainingId(@Param("training_id") Long trainingId,Pageable pageable);
    @Query(value = "select new com.example.main.domain.DTO.CommentDTO(c) from Comment as c where c.training.id = :training_id")
    List<CommentDTO> findComment(@Param("training_id")long trainingId,  Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO comment (local_date_time, training_id, user_id, text) VALUES (:create_time, :training_id, :user_id, :text)", nativeQuery = true)
    void saveComment(@Param("create_time")LocalDateTime createTime, @Param("training_id") Long trainingId, @Param("user_id") Long userId, @Param("text")String comment);
}
