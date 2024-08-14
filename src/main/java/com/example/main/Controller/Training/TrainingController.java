package com.example.main.Controller.Training;

import com.example.main.Service.TrainingService;
import com.example.main.domain.DTO.TrainingWithUsername;
import com.example.main.domain.Entity.Training;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("/v1/training")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new training"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Operation(description = "Method for create training")
    public ResponseEntity<TrainingWithUsername> createTraining(@RequestBody Training training, Principal principal) { //ToDo: Principal principal
        if (principal == null)
            trainingService.saveTraining(training, adminUsername);
        else
            trainingService.saveTraining(training, principal.getName());
        return new ResponseEntity<>(new TrainingWithUsername(training), HttpStatus.CREATED);
    }

    @GetMapping("/v1/trainings")
    @ResponseBody
    public ResponseEntity<List<TrainingWithUsername>> getTrainings(
            @RequestParam(defaultValue = "10")@Max(value = 100, message = "Limit should be less than 100")@Min(1) Integer limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam int userId) {
        return new ResponseEntity<>(trainingService.getTrainings(limit, offset, userId), HttpStatus.OK);
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

    @GetMapping("/v1/training/v1/findByUserId")
    @ResponseBody
    public ResponseEntity<List<TrainingWithUsername>> findTrainingByUser(@RequestParam(name = "user_id") Long userId) {
        return new ResponseEntity<>(trainingService.findTrainingByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/v1/training/v1/following")
    @ResponseBody
    public ResponseEntity<List<TrainingWithUsername>> findTrainingFollowingUsers(@RequestParam(name = "user_id")Long userId,
                                                                                 @RequestParam(defaultValue = "0") int offset,
                                                                                 @RequestParam(defaultValue = "10") @Max(100) @Min(1) int limit){
        return new ResponseEntity<>(trainingService.getTrainingFollowingUsers(limit,offset, userId), HttpStatus.OK);
    }
}
