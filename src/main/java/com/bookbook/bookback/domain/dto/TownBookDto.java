package com.bookbook.bookback.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TownBookDto {
    private String username;
    private String image;
    private String description;
    private String author;
    private int price;
    private String state;
    private String category;
}
