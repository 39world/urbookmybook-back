package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.ChatRoom;
import com.bookbook.bookback.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser(User user);
    Optional<ChatRoom> findByRoomName(String roomName);
}
