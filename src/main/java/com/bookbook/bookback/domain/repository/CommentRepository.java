package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTownBookId(Long townBookId);
    List<Comment> findByUser(User user);
    void deleteByTownBookId(Long townBookId);



}
