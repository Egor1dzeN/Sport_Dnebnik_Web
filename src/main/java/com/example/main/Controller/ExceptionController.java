package com.example.main.Controller;

import com.example.main.MyException.InvalidDataException;
import com.example.main.MyException.UserAlreadyExistException;
import com.example.main.MyException.UserEmailException;
import com.example.main.MyException.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;
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
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException) {
        var map = new HashMap<String, String>();
        map.put("error_msg", userAlreadyExistException.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(AuthenticationException userAlreadyExistException) {
        var map = new HashMap<String, String>();
        map.put("error_msg", userAlreadyExistException.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidDataException.class, UserEmailException.class})
    public ResponseEntity<?> handleInvalidDataException(RuntimeException invalidDataException){
        var map = new HashMap<String, String>();
        map.put("error_msg", invalidDataException.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleInvalidDataException(ConstraintViolationException constraintViolationException){
        var map = new HashMap<String, String>();
        map.put("error_msg", constraintViolationException.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
