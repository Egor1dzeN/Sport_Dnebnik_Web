package com.example.main.Service;

import com.example.main.MyException.NotFoundException;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.Repository.CommentRepository;
import com.example.main.Repository.LikesRepository;
import com.example.main.Repository.TrainingRepository;
import com.example.main.Repository.UserRepository;
import com.example.main.domain.Entity.Likes;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class LikeService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final LikesRepository likesRepository;

    public boolean like(long trainingId, long userId) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(() -> new NotFoundException("Training not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Optional<Likes> likes = likesRepository.findByTrainingAndUser(training, user);
        if (likes.isPresent()) {
            likesRepository.delete(likes.get());
            return false;
        } else {
            Likes likes1 = new Likes(training, user);
            likesRepository.save(likes1);
            return true;
        }
    }
}
