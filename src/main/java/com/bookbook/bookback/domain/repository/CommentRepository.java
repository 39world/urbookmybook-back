package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
