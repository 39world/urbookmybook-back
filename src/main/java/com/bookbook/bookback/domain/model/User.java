package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN

    private String image;
    private String town;
//    private String address;
    private String comment;
    private String myBook; //추후 구체적으로 수정 필요 ex) JSON or List<String> or List<int>
    private Double star;  //별점 업데이트 추가 필요. 업데이트 시점, 평점 내는 로직 구현
    // OAuth를 위해 구성한 추가 필드 2개
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createdAt;

//    [
//    private String username
//    private String email
//    private String image
//
//
//     public void update(userdto userdto)
//     this.username = userDto.getUsername();
//     this.image = userDto.getImage();
//    ]


    public User(UserDto userDto){
        this.email = userDto.getEmail();
        this.username = userDto.getUsername();
        this.image = userDto.getImage();
    }

    public void update(UserDto userDto){
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.image = userDto.getImage();
        this.town = userDto.getTown();
        this.comment = userDto.getComment();
    }
}
