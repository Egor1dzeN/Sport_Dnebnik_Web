package com.example.main.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@SpringBootTest
@Data
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void isValidEmail1() {
        String email = "hello@mail.ru";
        assertTrue(emailService.isValidEmail(email));
    }
    @Test
    void isValidEmail2() {
        String email = "hello@@mail.ru";
        assertFalse(emailService.isValidEmail(email));
    }
}