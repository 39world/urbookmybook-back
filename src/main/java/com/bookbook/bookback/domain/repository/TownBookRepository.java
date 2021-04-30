package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.TownBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownBookRepository extends JpaRepository<TownBook, Long> {
}
