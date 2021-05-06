package com.bookbook.bookback.domain.dto;

import com.bookbook.bookback.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.repository.NoRepositoryBean;

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

    //간단한 유저 정보 Dto 생성. 유저 정보 제공용
    public UserDto(String token, User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.image = user.getImage();
        this.town = user.getTown();
        this.comment = user.getComment();
        this.star = user.getStar();
        this.token = token;
    }

    //유저 프로필 업데이트용 Dto 생성
//    public UserDto(User user, JSONObject userJson, String fileUrl){
//        this.id = user.getId();
//        this.email = user.getEmail();
//        this.username = userJson.getString("username");
//        if(fileUrl.isEmpty()){
//            this.image = user.getImage();
//        } else {
//            this.image = fileUrl;
//        }
//        this.town = userJson.getString("town");
//        this.comment = userJson.getString("comment");
//    }

        public UserDto(User user, JSONObject userJson){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = userJson.getString("username");
        if(userJson.getString("image")==null){
            this.image = user.getImage();
        } else{
            this.image = userJson.getString("image");
        }
        this.image = user.getImage();
        this.town = userJson.getString("town");
        this.comment = userJson.getString("comment");
    }

}
