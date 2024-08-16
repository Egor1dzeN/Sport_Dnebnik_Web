package com.example.main.Controller;

import com.example.main.Repository.UserRepository;
import com.example.main.Service.UserService;
import com.example.main.domain.Entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.Multipart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/v1/user/v1/getUserById")
    @Operation()
    public ResponseEntity<?> getUserById(@RequestParam(value = "user_id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
    @DeleteMapping("/v1/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")long id){
        userService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
    /*
    @PostMapping("/v1/user/v1/avatar/{id}")
    public ResponseEntity<?> setAvatar(@PathVariable("id") long id, @RequestParam("file")MultipartFile image) throws IOException {
        User user = userService.getUserById(id).get();
        user.setAvatar(image.getBytes());
        userService.save(user);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }
    @GetMapping("/v1/user/v1/avatar/{id}")
    @Transactional
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getAvatar() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return ResponseEntity.ok().headers(headers).body(user.getAvatar());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
     */
}
