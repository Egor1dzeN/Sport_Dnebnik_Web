package com.example.main.MyException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(long id) {
        super("User with ID: " + id + " not found");

    }
}
