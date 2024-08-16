package com.example.main.Service;

import com.example.main.MyException.NotFoundException;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.Repository.TrainingRepository;
import com.example.main.domain.Entity.Comment;
import com.example.main.Repository.CommentRepository;
import com.example.main.Repository.UserRepository;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class CommentService {
    private final CommentRepository commentRepository;

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public List<Comment> getAllCommentByTrainingIdAndLimitAndOffset(Long trainingId, int limit, int offset) {
        return commentRepository.findByTrainingId(trainingId, PageRequest.of(offset, limit));
    }
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }
    public Comment save(Long trainingId, Long userId, String text) {
        return save(LocalDateTime.now(), trainingId, userId, text);
    }

    public Comment save(LocalDateTime createTime, Long trainingId, Long userId, String text) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Training training = trainingRepository.findById(trainingId).orElseThrow(() -> new NotFoundException("Training with ID: "+trainingId+" not found"));
        Comment comment = new Comment(training, user, text);
        return save(comment);
    }
}
