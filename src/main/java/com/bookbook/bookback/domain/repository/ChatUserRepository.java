package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

}
