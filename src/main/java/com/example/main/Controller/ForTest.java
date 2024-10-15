package com.example.main.Controller;


import com.example.main.Config.KafkaConfig;
import com.example.main.Service.KafkaSender;
import com.example.main.Service.TrainingService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;


@Controller
@Data
public class ForTest {
    private final TrainingService trainingService;
    private final KafkaSender kafkaSender;

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Model model) {
        model.addAttribute("errorMessage", "The page you are looking for does not exist.");
        return "error/404";
    }
    @GetMapping("/v1/test")
    public String send(){
        return "test";
    }

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> method() {
        return new ResponseEntity<>("HELLO", HttpStatus.OK);
    }




}
