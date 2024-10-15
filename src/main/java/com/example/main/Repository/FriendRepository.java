package com.example.main.Repository;

import com.example.main.domain.Entity.Friend;
import com.example.main.domain.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUser1IdAndStatusIn(Long userId, List<FriendStatus> status);
    List<Friend> findByUser2IdAndStatusIn(Long userId, List<FriendStatus> status);

    Optional<Friend> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}
