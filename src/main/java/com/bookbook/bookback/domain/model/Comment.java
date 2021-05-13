package com.bookbook.bookback.domain.model;


import com.bookbook.bookback.domain.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable= false)
//    private String username;

    @Column(columnDefinition = "TEXT",nullable=false)
    private String contents;

//    ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="town_book_id", nullable=false)
//    private TownBook townBook;

    @Column(name="town_book_id", nullable=false)
    private Long townBookId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private Long parentId;

//    @OneToMany(mappedBy = "parent", orphanRemoval = true)
//    private List<Comment> children = new ArrayList<>();






    public Comment(CommentDto commentDto){
//        this.username=commentDto.getUser().getUsername();
        this.contents=commentDto.getContents();
        this.user=commentDto.getUser();
        this.townBookId=commentDto.getTownBookId();
        this.parentId=commentDto.getParentId();
    }
    public void update(CommentDto commentDto){
        this.contents=commentDto.getContents();
    }
}
