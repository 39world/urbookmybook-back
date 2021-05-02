package com.bookbook.bookback.domain.dto;

import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import lombok.Data;


@Data
public class CommentDto {

    private Long id;

    private String contents;

    private User user;

    private TownBook townBook;
}
