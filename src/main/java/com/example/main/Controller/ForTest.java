package com.example.main.Controller;


import com.example.main.Entity.Training;
import com.example.main.Object.TrainingWithUsername;
import com.example.main.Service.TrainingService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Controller
@Data
public class ForTest {
    private final TrainingService trainingService;

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Model model) {
        model.addAttribute("errorMessage", "The page you are looking for does not exist.");
        return "error/404";
    }

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> method() {
        return new ResponseEntity<>("HELLO", HttpStatus.OK);
    }

    @PostMapping("/v1/training/v1/create")
    @ResponseBody
    public ResponseEntity<Void> method2(@RequestBody Training training) {
        trainingService.saveTraining(training, "e");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/v1/training/v1/get_all")
    @ResponseBody
    public ResponseEntity<List<TrainingWithUsername>> getAllTrainings() {
        return new ResponseEntity<>(trainingService.getAllTraining(), HttpStatus.OK);
    }

    @GetMapping("/v1/training/test")
    @ResponseBody
    public ResponseEntity<String> getAllTrainings1() {
        System.out.println("XSS ATACK");
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