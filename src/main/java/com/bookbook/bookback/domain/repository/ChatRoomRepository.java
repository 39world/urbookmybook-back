package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUserInterested(String userInterested);
}
