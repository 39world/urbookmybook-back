package com.bookbook.bookback.domain.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class TownBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable= false)
    private String author;

    @Column(nullable= false)
    private String image;

    @Column(nullable= false)
    private String status;

    @Column(columnDefinition = "TEXT", nullable= false)
    private String description;

    @Column(nullable= false)
    private String category;

    @Column(nullable= false)
    private Integer price;
}
