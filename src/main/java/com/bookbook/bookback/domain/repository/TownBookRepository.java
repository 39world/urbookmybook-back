package com.bookbook.bookback.domain.repository;

import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.TownBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownBookRepository extends JpaRepository<TownBook,Long> {

    List<TownBook> findAll();


    List<TownBook> findByTitleContainingOrderByModifiedAtDesc(String keyword);

    Page<TownBook> findByTownOrderByCreatedAtDesc(String town, Pageable pageable);

    List <TownBook> findByCategoryContainingOrderByModifiedAtDesc(String category);

    List<TownBook> findByUserEmail(String Email);

}
