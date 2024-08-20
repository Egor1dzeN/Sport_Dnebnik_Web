package com.example.main.Service;

import com.example.main.Repository.CommentRepository;
import com.example.main.Repository.LikesRepository;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.domain.DTO.TrainingDTO;
import com.example.main.Repository.TrainingRepository;
import com.example.main.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class TrainingService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final FriendService friendService;

    public User save(User user, Training training) {
        training.setUser(user);
        user.addTraining(training);
        return userRepository.save(user);
    }

    public User save(String username, Training training) {
        User user = userRepository.findByUsername(username);
        return save(user, training);
    }

    @Transactional
    public void deleteTraining(Long trainingId) {
        if (trainingRepository.existsById(trainingId)) {
            trainingRepository.deleteById(trainingId);
        }
    }

    public Page<TrainingDTO> getTrainings(int limit, int offset, int userId) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<TrainingDTO> trainingList = trainingRepository.findTrainings(pageable);
        userRepository.findById((long) userId).orElseThrow(() -> new UserNotFoundException(userId));
        for (var training : trainingList) {
            if (likesRepository.existsByUserIdAndTrainingId((long) userId, training.getTrainingId())) {
                training.setLiked(true);
            }
            training.setCommentList(commentRepository.findComment(training.getTrainingId(), PageRequest.of(0, 10)));
            training.setCountLike(likesRepository.countAllByTrainingId(training.getTrainingId()));
        }
        return trainingList;
    }

    public Page<TrainingDTO> getTrainingFollowingUsers(int limit, int offset, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Long> listFollowingUsersId = friendService.getIdFollowing(userId);
        Page<TrainingDTO> trainingList = trainingRepository.findAllByUserIdIn(listFollowingUsersId, PageRequest.of(offset, limit));
        for (var training : trainingList) {
            if (likesRepository.existsByUserIdAndTrainingId(userId, training.getTrainingId())) {
                training.setLiked(true);
            }
            training.setCommentList(commentRepository.findComment(training.getTrainingId(), PageRequest.of(0, 10)));
            training.setCountLike(likesRepository.countAllByTrainingId(training.getTrainingId()));
        }
        return trainingList;
    }

    public Page<TrainingDTO> findTrainingByUserId(int limit, int offset, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Page<TrainingDTO> trainingList = trainingRepository.findAllByUserId(userId, PageRequest.of(offset, limit));
        for (var training : trainingList) {
            if (likesRepository.existsByUserIdAndTrainingId(userId, training.getTrainingId())) {
                training.setLiked(true);
            }
            training.setCommentList(commentRepository.findComment(training.getTrainingId(), PageRequest.of(0, 10)));
            training.setCountLike(likesRepository.countAllByTrainingId(training.getTrainingId()));
        }
        return trainingList;
    }

    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

}
