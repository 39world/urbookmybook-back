package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.TownBookDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TownBook extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Column(name = "username", nullable = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "image", nullable = true)
    private  String image;

    @ElementCollection
    private List<String> captureImages;

    @Column(columnDefinition = "TEXT", name = "description",nullable = true)
    private String description;

    @Column(name = "author", nullable = true)
    private String author;

    @Column(name = "price", nullable = true)
    private int price;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "category", nullable = true)
    private String category;

    @Column(name="town", nullable=true)
    private String town;

    @Column(name="publisher", nullable=true)
    private String publisher;

    @Column(columnDefinition = "TEXT", name="contentInfo", nullable=true)
    private String contentInfo;

    @Column(columnDefinition = "TEXT", name="web_url", nullable=true)
    private String webUrl;

    @Column(name="views", nullable=true)
    private Integer views;

    @Column(nullable = true)
    private  int finish = 0;


    public TownBook(TownBookDto townBookDto){
        this.username=townBookDto.getUser().getUsername();
        this.title=townBookDto.getTitle();
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
        this.captureImages=townBookDto.getCaptureImages();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.status = townBookDto.getStatus();
        this.price = townBookDto.getPrice();
        this.town=townBookDto.getTown();
        this.publisher=townBookDto.getPublisher();
        this.contentInfo=townBookDto.getContentInfo();
        this.webUrl=townBookDto.getWebUrl();
        this.views=townBookDto.getViews();
        this.user = townBookDto.getUser();

    }

    public void update(TownBookDto townBookDto){
        this.title=townBookDto.getTitle();
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
        this.captureImages=townBookDto.getCaptureImages();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.status = townBookDto.getStatus();
        this.price = townBookDto.getPrice();
        this.publisher=townBookDto.getPublisher();
        this.contentInfo=townBookDto.getContentInfo();
        this.webUrl=townBookDto.getWebUrl();
    }


}
