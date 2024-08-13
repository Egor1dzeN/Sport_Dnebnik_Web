package com.example.main.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Data
@RequiredArgsConstructor
class CommentServiceTest {
    @Autowired
    private CommentService commentService;


}