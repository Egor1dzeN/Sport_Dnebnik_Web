package com.example.main.Controller.Training;

import com.example.main.Service.TrainingService;
import com.example.main.domain.DTO.TrainingDTOAndPage;
import com.example.main.domain.DTO.TrainingDTO;
import com.example.main.domain.Entity.Training;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Data
@Validated
public class TrainingController {
    @Value(value = "${admin.username}")
    private String adminUsername;

    private final TrainingService trainingService;

    //Создание новой тренировки
    @PostMapping("/v1/training")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new training"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Operation(description = "Method for create training")
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody Training training, Principal principal) { //ToDo: Principal principal
        if (principal == null)
            trainingService.save(adminUsername, training);
        else
            trainingService.save(principal.getName(), training);
        return new ResponseEntity<>(new TrainingDTO(training), HttpStatus.CREATED);
    }

    //Загрузка всех тренировок
    @GetMapping("/v1/trainings")
    @ResponseBody
    public ResponseEntity<TrainingDTOAndPage> getTrainings(
            @RequestParam(name = "user_id") int userId,
            @RequestParam(defaultValue = "10") @Max(value = 100, message = "Limit should be less than 100") @Min(1) Integer limit,
            @RequestParam(defaultValue = "0") int offset) {
//        Map<String, ?> map = new HashMap<>();
        return new ResponseEntity<>(new TrainingDTOAndPage(trainingService.getTrainings(limit, offset, userId)), HttpStatus.OK);
    }
    @DeleteMapping("/v1/training/delete/{id}")
    public ResponseEntity<?> deleteTraining(@PathVariable("id") Long trainingId) {
        System.out.println(trainingId);
        trainingService.deleteTraining(trainingId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
    @GetMapping("/v1/training/test")
    @ResponseBody
    public ResponseEntity<String> getAllTrainings1() {
//        System.out.println("XSS ATACK");
        return new ResponseEntity<>("ahhshsh", HttpStatus.OK);
    }

    @GetMapping("/test")
    public String method1() {
        return "add_training/add_training";
    }


    //Для загрузки тренировок пользователя
    @GetMapping("/v1/training/v1/findByUserId")
    @ResponseBody
    public ResponseEntity<TrainingDTOAndPage> findTrainingByUser(@RequestParam(name = "user_id") Long userId,
                                                                @RequestParam(defaultValue = "0") int offset,
                                                                @RequestParam(defaultValue = "10") @Max(100) @Min(1) int limit) {
        return new ResponseEntity<>(new TrainingDTOAndPage(trainingService.findTrainingByUserId(limit, offset, userId)), HttpStatus.OK);
    }

    //Загрузка тренировок ппользователей, на которых подисан
    @GetMapping("/v1/training/v1/following")
    @ResponseBody
    public ResponseEntity<TrainingDTOAndPage> findTrainingFollowingUsers(@RequestParam(name = "user_id") Long userId,
                                                                        @RequestParam(defaultValue = "0") int offset,
                                                                        @RequestParam(defaultValue = "10") @Max(100) @Min(1) int limit) {
        return new ResponseEntity<>(new TrainingDTOAndPage(trainingService.getTrainingFollowingUsers(limit, offset, userId)), HttpStatus.OK);
    }
}
