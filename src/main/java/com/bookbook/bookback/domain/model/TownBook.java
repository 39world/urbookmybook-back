package com.bookbook.bookback.domain.model;


import com.bookbook.bookback.domain.dto.TownBookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TownBook extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "townBookId", nullable = true)
    private Long townBookId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "description",nullable = true)
    private String description;

    @Column(name = "author", nullable = true)
    private String author;

    @Column(name = "price", nullable = true)
    private int price;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "category", nullable = true)
    private String category;






    public TownBook(TownBookDto townBookDto, User user){
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.state = townBookDto.getState();
        this.price = townBookDto.getPrice();
        this.user = user;


    }
    public void update(TownBookDto townBookDto){
        this.author = townBookDto.getAuthor();
        this.image = townBookDto.getImage();
        this.category =townBookDto.getCategory();
        this.description =townBookDto.getDescription();
        this.state = townBookDto.getState();
        this.price = townBookDto.getPrice();

    }




}
