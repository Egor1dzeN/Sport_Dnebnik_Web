package com.example.main;


import com.example.main.Repository.TrainingRepository;
import com.example.main.Repository.UserRepository;
import com.example.main.Service.CommentService;
import com.example.main.Service.TrainingService;
import com.example.main.Service.UserService;
import com.example.main.domain.Entity.Comment;
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
    private final UserService userService;
    private final TrainingRepository trainingRepository;
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

    private final List<User> usersIdList = new ArrayList<>();
    private final List<Training> trainingIdList = new ArrayList<>();
//    private final List<Training> trainingIdList = new ArrayList<>();
    @Override
    @Transactional
    public void run(String... args){
        createSuperAdmin();
        createSuperUser();
        createBasicTrainingsAndComments();
//        createCommentForBasicTraining();
        createComments();
    }
    public void createSuperAdmin(){
        logger.info("Created super admin");
        if (!userRepository.existsByUsername(adminUsername)) {
            User user = new User(adminUsername, passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN);
            user.setName("Name admin");
            user.setSurname("Surname admin");
            user = userRepository.save(user);
            usersIdList.add(user);
        }
    }
    public void createSuperUser(){
        logger.info("Created super user");
        if (!userRepository.existsByUsername(userUsername)) {
            User user = new User(userUsername, passwordEncoder.encode(userPassword), Role.ROLE_USER);
            user.setName("Name user");
            user.setSurname("Surname user");
            user = userRepository.save(user);
            usersIdList.add(user);
        }
    }
    public void createBasicTrainingsAndComments(){
        logger.info("Created basic trainings");
        LocalTime lt1 = LocalTime.of(1, 0);
        LocalDateTime ldt1 = LocalDateTime.of(2024, 5, 5, 10, 0);
        Training training1 = new Training(TypeTraining.RUN, 10, lt1, ldt1);
        training1.setComment("My first training!");
        usersIdList.getFirst().addTraining(training1);
        trainingService.save(usersIdList.get(0), training1);

        LocalTime lt2 = LocalTime.of(2, 0);
        LocalDateTime ldt2 = LocalDateTime.of(2024, 5, 5, 10, 0);
        Training training2 = new Training(TypeTraining.CYCLE, 50, lt2, ldt2);
        training2.setComment("Good afternoon CYCLE :)");
        trainingService.save(usersIdList.get(1), training2);

    }
    public void createComments(){
        List<Training> training = trainingRepository.findAll();
        Comment comment1 = new Comment(training.getFirst(), usersIdList.get(0), "Comment for training 1");
        commentService.save(comment1);
        Comment comment2 = new Comment(training.get(1), usersIdList.get(1), "Comment for training 2");
        commentService.save(comment2);
    }

}
