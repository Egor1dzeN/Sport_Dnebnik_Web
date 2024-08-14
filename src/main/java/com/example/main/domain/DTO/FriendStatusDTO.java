package com.example.main.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendStatusDTO {
    boolean mySubscribe;
    boolean friendSubscribe;
}
