package com.example.main.Controller;

import com.example.main.domain.Entity.Comment;
import com.example.main.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/v1/training/v1/getAllComment")
    public ResponseEntity<?> getAllCommentByTrainingId(@RequestParam(name = "training_id") Long id) {
        var listComment = commentService.getAllCommentByTrainingId(id);
        List<HashMap<String, String>> list = new ArrayList<>();
        for (Comment comment : listComment) {
            var map = new HashMap<String, String>();
            map.put("username", comment.getUser().getUsername());
            map.put("text", comment.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            map.put("date", comment.getLocalDateTime().format(formatter));
            list.add(map);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/v1/training/v1/addComment")
    public ResponseEntity<HashMap<?,?>> addComment(@RequestParam(name = "training_id") Long trainingId, @RequestParam(name = "user_id") Long userId, @RequestBody String text) {
        System.out.println(text);
        var user = commentService.addComment(userId, trainingId, text);
        var map = new HashMap<String, String>();
        map.put("username", user.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        map.put("date", LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
