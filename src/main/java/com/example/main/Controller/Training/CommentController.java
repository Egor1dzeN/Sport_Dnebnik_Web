package com.example.main.Controller.Training;

import com.example.main.Service.UserService;
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

    @GetMapping("/v1/training/v1/comments")
    public ResponseEntity<?> getCommentsByTrainingId(@RequestParam(name = "training_id") Long id,
                                                     @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                     @RequestParam(name = "offset", defaultValue = "0") int offset) {
        var listComment = commentService.getAllCommentByTrainingIdAndLimitAndOffset(id, limit, offset);
//        System.out.println(listComment);
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

    @PostMapping("/v1/training/v1/comment")
    public ResponseEntity<HashMap<?, ?>> addComment(@RequestParam(name = "training_id") Long trainingId,
                                                    @RequestParam(name = "user_id") Long userId,
                                                    @RequestParam(name = "text") String text) {

        Comment comment = commentService.save(trainingId, userId, text);

        var map = new HashMap<String, String>();
        map.put("username", comment.getUser().getUsername());
        map.put("training_id", comment.getTraining().getId()+"");
        map.put("text", comment.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        map.put("date", LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
