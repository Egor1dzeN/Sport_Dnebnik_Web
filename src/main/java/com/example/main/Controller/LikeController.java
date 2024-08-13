package com.example.main.Controller;

import com.example.main.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PutMapping("/v1/training/v1/like")
    public ResponseEntity<?> like(@RequestParam("training_id") long trainingId, @RequestParam("user_id")long userId){
        boolean is_liked = likeService.like(trainingId, userId);

        var map = new HashMap<String, Boolean>();
        map.put("is_likes", is_liked);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
