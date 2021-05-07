package com.bookbook.bookback.domain.dto;

import com.bookbook.bookback.domain.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TownBookDto {
    private String title;
    private String image;
    private List<String> captureImages;
    private String description;
    private String author;
    private int price;
    private String status;
    private String category;
    private User user;

}
