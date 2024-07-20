package com.example.main.Controller;

import com.example.main.MyException.UserNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException userNotFoundException) {
        var map = new HashMap<String, String>();
        map.put("error_msg", userNotFoundException.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
