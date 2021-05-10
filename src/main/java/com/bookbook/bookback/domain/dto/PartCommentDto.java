package com.bookbook.bookback.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartCommentDto {

    private Long commentId;
    private String username;
    private String contents;
    private String image;


    private String email;
    private Long userId;


    public PartCommentDto(Long commentId, String username, String cotents, String image, String email, Long userId){
        this.commentId= commentId;
        this.username= username;
        this.contents= cotents;
        this.image= image;

        this.email=email;
        this.userId=userId;

    }

}
