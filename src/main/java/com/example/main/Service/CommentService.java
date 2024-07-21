package com.example.main.Service;

import com.example.main.domain.Entity.Comment;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import com.example.main.Repository.CommentRepository;
import com.example.main.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public List<Comment> getAllCommentByTrainingId(Long trainingId) {
        return commentRepository.findAllByTrainingId(trainingId);
    }

    public User addComment(Long userId, Long trainingId, String text) {
//        commentRepository.insertComment(trainingId, userId, text, LocalDateTime.now());
        User user = userRepository.findAllById(userId);
        Training training = entityManager.getReference(Training.class, trainingId);
        Comment comment = new Comment(training, user, text);
        commentRepository.save(comment);
        return user;
    }
}
