package com.bookbook.bookback.domain.dto;

import com.bookbook.bookback.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String image;
    private String town;
    private String comment;
    private Double star;
    private String token;

    private List<Long> scrapList;

    private Long point;


    //간단한 유저 정보 Dto 생성. 유저 정보 제공용
    public UserDto(String token, User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.image = user.getImage();
        this.town = user.getTown();
        this.comment = user.getComment();
        this.star = user.getStar();

        this.scrapList=user.getScrapList();

        this.point = user.getPoint();

        this.token = token;

    }


        public UserDto(User user, JSONObject userJson){
        this.id = user.getId();
        this.email = user.getEmail();
        if(userJson.isNull("username")){
                this.username = user.getUsername();
        } else{
                this.username = userJson.getString("username");
        }
        if(userJson.isNull("image")){
            this.image = user.getImage();
        } else{
            this.image = userJson.getString("image");
        }
        if(userJson.isNull("town")){
            this.town = user.getTown();
        } else{
            this.town = userJson.getString("town");
        }
        if(userJson.isNull("comment")){
            this.comment = user.getComment();
        } else{
            this.comment = userJson.getString("comment");
        }
    }
}
