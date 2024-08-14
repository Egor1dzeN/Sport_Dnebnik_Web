package com.example.main.domain.DTO;

import com.example.main.domain.Entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long userId;
    private String username;
    private String text;
    private LocalDateTime localDateTime;
    public CommentDTO(Comment comment){
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.text = comment.getText();
        this.localDateTime = comment.getLocalDateTime();
    }
}
