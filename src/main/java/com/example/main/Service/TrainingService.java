package com.example.main.Service;

import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import com.example.main.MyException.UserNotFoundException;
import com.example.main.domain.DTO.TrainingWithUsername;
import com.example.main.Repository.TrainingRepository;
import com.example.main.Repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TrainingService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public void saveTraining(Training training, String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User wasn't found, username - " + username));
        training.setUser(user);
        trainingRepository.save(training);
//        System.out.println("Training is saved " + training);
    }

    public List<TrainingWithUsername> getAllTraining() {
        List<TrainingWithUsername> list = trainingRepository.findAllTraining();
//        System.out.println(list);
        return list;
    }
    public List<TrainingWithUsername> findTrainingByUserId(Long userId){
        List<TrainingWithUsername> list = trainingRepository.findAllByUserId(userId);
//        System.out.println(list);
        return list;
    }
}
