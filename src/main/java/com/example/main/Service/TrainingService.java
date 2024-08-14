package com.example.main.Service;

import com.example.main.Repository.CommentRepository;
import com.example.main.Repository.LikesRepository;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.domain.DTO.TrainingWithUsername;
import com.example.main.Repository.TrainingRepository;
import com.example.main.Repository.UserRepository;
import lombok.Data;
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

    public Training saveTraining(Training training, String username){
        Optional<User> optionalUser = Optional.of(userRepository.findByUsername(username));
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User wasn't found, username - " + username));
        training.setUser(user);
        return trainingRepository.save(training);
//        System.out.println("Training is saved " + training);
    }

    public List<TrainingWithUsername> getTrainings(int limit, int offset, int userId) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<TrainingWithUsername> trainingList = trainingRepository.findTrainings(pageable);
        userRepository.findById((long)userId).orElseThrow(()->new UserNotFoundException(userId));
        for (var training : trainingList){
            if (likesRepository.existsByUserIdAndTrainingId((long)userId, training.getTrainingId())){
                training.setLiked(true);
            }
            training.setCommentList(commentRepository.findComment(training.getTrainingId(), PageRequest.of(0, 10)));
            training.setCountLike(likesRepository.countAllByTrainingId(training.getTrainingId()));
        }
        return trainingList;
    }
    public List<TrainingWithUsername> getTrainingFollowingUsers(int limit, int offset, long userId){
        userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        List<Long> listFollowingUsersId = friendService.getIdFollowing(userId);
        List<TrainingWithUsername> trainingList = trainingRepository.findAllByUserIdIn(listFollowingUsersId, PageRequest.of(offset, limit));
        for (var training : trainingList){
            if (likesRepository.existsByUserIdAndTrainingId(userId, training.getTrainingId())){
                training.setLiked(true);
            }
            training.setCommentList(commentRepository.findComment(training.getTrainingId(), PageRequest.of(0, 10)));
            training.setCountLike(likesRepository.countAllByTrainingId(training.getTrainingId()));
        }
        return trainingList;
    }
    public List<TrainingWithUsername> findTrainingByUserId(Long userId){
        //        System.out.println(list);
        return trainingRepository.findAllByUserId(userId);
    }
    public Optional<Training> findById(Long id){
        return trainingRepository.findById(id);
    }
}
