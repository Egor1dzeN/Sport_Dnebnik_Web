package com.example.main.Controller;

import com.example.main.Repository.UserRepository;
import com.example.main.Service.FriendService;
import com.example.main.Service.UserService;
import com.example.main.domain.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final FriendService friendService;

    @GetMapping("/v1/user/v1/getUserById")
    public ResponseEntity<?> getUserById(@RequestParam(value = "user_id") Long userId, @RequestParam(value = "friend_id") Long friendId) {
        UserDTO userDTO = new UserDTO(userService.getUserById(friendId).get());
        userDTO.setFriendStatusDTO(friendService.getStatus(userId, friendId));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
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
