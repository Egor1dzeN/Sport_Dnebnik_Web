package com.example.main.Service;

import com.example.main.MyException.UserNotFoundException;
import com.example.main.Repository.FriendRepository;
import com.example.main.Repository.UserRepository;
import com.example.main.domain.DTO.FriendStatusDTO;
import com.example.main.domain.Entity.Friend;
import com.example.main.domain.Entity.User;
import com.example.main.domain.enums.FriendStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //Возвращает список id пользователей на которых userId подписан
    public List<Long> getIdFollowing(Long userId) {
        List<Long> listId = new ArrayList<>();
        List<Friend> from_left_to_right = friendRepository.findByUser1IdAndStatusIn(userId, List.of(FriendStatus.FROM_LEFT_TO_RIGHT, FriendStatus.MUTUALLY));
        List<Long> id_from_left_to_right = from_left_to_right.stream()
                .map((el) -> el.getUser2().getId()).toList();
        List<Friend> from_right_to_left = friendRepository.findByUser2IdAndStatusIn(userId, List.of(FriendStatus.FROM_RIGHT_TO_LEFT, FriendStatus.MUTUALLY));
        List<Long> id_from_right_to_left = from_right_to_left.stream()
                .map((el) -> el.getUser1().getId()).toList();
        listId.addAll(id_from_left_to_right);
        listId.addAll(id_from_right_to_left);
        return listId;
    }

    //Добавляет в друзья и выводит текущий статус дружбы
    public FriendStatusDTO addFriend(Long userId, Long friendId) {
        User meUser = userRepository.findAllById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User friendUser = userRepository.findAllById(friendId).orElseThrow(() -> new UserNotFoundException(friendId));
        Optional<Friend> optFriend1 = friendRepository.findByUser1IdAndUser2Id(userId, friendId);
        if (optFriend1.isPresent()) {
            Friend friend = optFriend1.get();
            switch (optFriend1.get().getStatus()) {
                case MUTUALLY -> {
                    friend.setStatus(FriendStatus.FROM_RIGHT_TO_LEFT);
                    friendRepository.save(friend);
                    return new FriendStatusDTO(false, true);
                }
                case FROM_LEFT_TO_RIGHT -> {
                    friendRepository.delete(friend);
                    return new FriendStatusDTO(false, false);
                }
                case FROM_RIGHT_TO_LEFT -> {
                    friend.setStatus(FriendStatus.MUTUALLY);
                    friendRepository.save(friend);
                    return new FriendStatusDTO(true, true);
                }
            }
        }
        Optional<Friend> optFriend2 = friendRepository.findByUser1IdAndUser2Id(friendId, userId);
        if (optFriend2.isPresent()) {
            Friend friend = optFriend2.get();
            switch (optFriend2.get().getStatus()) {
                case MUTUALLY -> {
                    friend.setStatus(FriendStatus.FROM_LEFT_TO_RIGHT);
                    friendRepository.save(friend);
                    return new FriendStatusDTO(false, true);
                }
                case FROM_LEFT_TO_RIGHT -> {
                    friend.setStatus(FriendStatus.MUTUALLY);
                    friendRepository.save(friend);
                    return new FriendStatusDTO(true, true);

                }
                case FROM_RIGHT_TO_LEFT -> {
                    friendRepository.delete(friend);
                    return new FriendStatusDTO(false, false);
                }
            }
        }
        Friend friend = new Friend(meUser, friendUser, FriendStatus.FROM_LEFT_TO_RIGHT);
        friendRepository.save(friend);
        return new FriendStatusDTO(true, false);
    }
    public FriendStatusDTO getStatus(Long userId, Long friendId){
        Optional<Friend> optional1 = friendRepository.findByUser1IdAndUser2Id(userId, friendId);
        if (optional1.isPresent()){
            switch (optional1.get().getStatus()){
                case FROM_LEFT_TO_RIGHT -> {
                    return new FriendStatusDTO(true, false);
                }
                case FROM_RIGHT_TO_LEFT -> {
                    return new FriendStatusDTO(false, true);
                }
                case MUTUALLY -> {
                    return new FriendStatusDTO(true, true);
                }

            }
        }
        Optional<Friend> optional2 = friendRepository.findByUser1IdAndUser2Id(friendId, userId);
        if (optional1.isPresent()){
            switch (optional1.get().getStatus()){
                case FROM_LEFT_TO_RIGHT -> {
                    return new FriendStatusDTO(false, true);
                }
                case FROM_RIGHT_TO_LEFT -> {
                    return new FriendStatusDTO(true, false);
                }
                case MUTUALLY -> {
                    return new FriendStatusDTO(true, true);
                }

            }
        }
        return new FriendStatusDTO(false, false);
    }
}
