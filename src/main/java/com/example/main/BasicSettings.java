package com.example.main;


import com.example.main.Repository.UserRepository;
import com.example.main.Service.CommentService;
import com.example.main.Service.TrainingService;
import com.example.main.domain.Entity.Role;
import com.example.main.domain.Entity.Training;
import com.example.main.domain.Entity.User;
import com.example.main.domain.enums.TypeTraining;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class BasicSettings implements CommandLineRunner {
    @Value(value = "${admin.username}")
    private String adminUsername;
    @Value(value = "${admin.password}")
    private String adminPassword;
    @Value(value = "${user.username}")
    private String userUsername;
    @Value(value = "${user.password}")
    private String userPassword;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final TrainingService trainingService;
    private final CommentService commentService;

    private final Logger logger = LogManager.getLogger(BasicSettings.class);

    private final List<Long> usersIdList = new ArrayList<>();
    private final List<Long> trainingIdList = new ArrayList<>();
    @Override
    @Transactional
    public void run(String... args){
        createSuperAdmin();
        createSuperUser();
        createBasicTrainings();
        createCommentForBasicTraining();
    }
    public void createSuperAdmin(){
        logger.info("Created super admin");
        if (!userRepository.existsByUsername(adminUsername)) {
            User user = userRepository.save(new User(adminUsername, passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN));
            usersIdList.add(user.getId());
        }
    }
    public void createSuperUser(){
        logger.info("Created super user");
        if (!userRepository.existsByUsername(userUsername)) {
            User user = userRepository.save(new User(userUsername, passwordEncoder.encode(userPassword), Role.ROLE_USER));
            usersIdList.add(user.getId());
        }
    }
    public void createBasicTrainings(){
        logger.info("Created basic trainings");
        LocalTime lt1 = LocalTime.of(1, 0);
        LocalDateTime ldt1 = LocalDateTime.of(2024, 5, 5, 10, 0);
        Training training1 = new Training(TypeTraining.RUN, 10, lt1, ldt1);
        Training training_1 = trainingService.saveTraining(training1, adminUsername);
        trainingIdList.add(training_1.getId());

        LocalTime lt2 = LocalTime.of(2, 0);
        LocalDateTime ldt2 = LocalDateTime.of(2024, 5, 5, 10, 0);
        Training training2 = new Training(TypeTraining.CYCLE, 50, lt2, ldt2);
        Training training_2 = trainingService.saveTraining(training2, userUsername);
        trainingIdList.add(training_2.getId());
    }
    public void createCommentForBasicTraining(){
        logger.info("Created basic comments");
        for(Long trainingId : trainingIdList){
            for(Long userId : usersIdList){
                commentService.save(trainingId, userId, "comment from user - "+userId+" :)");
            }
        }
    }

}
