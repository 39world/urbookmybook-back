package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.ChatRoom;
import com.bookbook.bookback.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser(User user);
}
