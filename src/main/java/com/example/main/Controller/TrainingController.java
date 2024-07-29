package com.example.main.Controller;

import com.example.main.Service.TrainingService;
import com.example.main.domain.DTO.TrainingWithUsername;
import com.example.main.domain.Entity.Training;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Data
public class TrainingController {
    private final TrainingService trainingService;

    @PostMapping("/v1/training/v1/create")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "TEST")
    })
    @Operation(description = "Method for create training")
    public ResponseEntity<Void> method2(@RequestBody Training training){ //ToDo: Principal principal
        trainingService.saveTraining(training, "e");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/v1/training/v1/get_all")
    @ResponseBody
    public ResponseEntity<List<TrainingWithUsername>> getAllTrainings() {
        return new ResponseEntity<>(trainingService.getAllTraining(), HttpStatus.OK);
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
}