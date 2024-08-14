package com.example.main.Controller.Friend;

import com.example.main.Service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PutMapping("/v1/friend")
    public ResponseEntity<?> addFriend(@RequestParam(name = "user_id") Long userId, @RequestParam(name = "friend_id") Long friendId) {
        return new ResponseEntity<>(friendService.addFriend(userId, friendId), HttpStatus.CREATED);
    }
}
