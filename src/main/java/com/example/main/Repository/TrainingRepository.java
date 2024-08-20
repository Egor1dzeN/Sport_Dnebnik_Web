package com.example.main.Repository;

import com.example.main.domain.Entity.Training;
import com.example.main.domain.DTO.TrainingDTO;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query(value = "SELECT new com.example.main.domain.DTO.TrainingDTO(t) FROM Training as t ")
    Page<TrainingDTO> findTrainings(Pageable pageable);
    @Query ("SELECT new com.example.main.domain.DTO.TrainingDTO(t) FROM Training as t where t.user.id = :user_id")
    Page<TrainingDTO> findAllByUserId(@Param("user_id") Long userId, Pageable pageable);

    @Query("SELECT new com.example.main.domain.DTO.TrainingDTO(t) FROM Training as t where t.user.id IN :list_users_id")
    Page<TrainingDTO> findAllByUserIdIn(@Param("list_users_id") List<Long> listUsersId, Pageable pageable);
    @Nonnull
    Optional<Training> findById(@Nonnull Long id);
}
