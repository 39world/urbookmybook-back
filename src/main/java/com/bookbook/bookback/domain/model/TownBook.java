package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.TownBookDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "image", nullable = true)
    private  String image;

    @Column(name = "description",nullable = true)
    private String description;

    @Column(name = "author", nullable = true)
    private String author;

    @Column(name = "price", nullable = true)
    private int price;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "category", nullable = true)
    private String category;


//    @ElementCollection
//    private List<String> captureImages;

    public TownBook(TownBookDto townBookDto){
        this.username=townBookDto.getUser().getUsername();
        this.title=townBookDto.getTitle();
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
//        this.captureImages=townBookDto.getCaptureImages();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.status = townBookDto.getStatus();
        this.price = townBookDto.getPrice();
        this.user = townBookDto.getUser();
    }

    public void update(TownBookDto townBookDto){
        this.title=townBookDto.getTitle();
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
//        this.captureImages=townBookDto.getCaptureImages();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.status = townBookDto.getStatus();
        this.price = townBookDto.getPrice();

    }




}
