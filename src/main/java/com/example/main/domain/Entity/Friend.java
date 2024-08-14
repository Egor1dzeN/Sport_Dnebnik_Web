package com.example.main.domain.Entity;

import com.example.main.domain.enums.FriendStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "friend")
@Data
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    private FriendStatus status;

    public Friend(User user1, User user2, FriendStatus status) {
        this.status = status;
        this.user2 = user2;
        this.user1 = user1;
    }
}
