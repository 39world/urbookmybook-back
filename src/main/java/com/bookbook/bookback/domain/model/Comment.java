package com.bookbook.bookback.domain.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable= false)
    private String username;

    @Column(nullable=false)
    private String contents;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="TOWNBOOK_ID", nullable=false)
    private TownBook townBook;

}
