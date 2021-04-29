package com.bookbook.bookback.domain.dto;


import lombok.Data;

@Data
public class UserDto {

    //뷰에서 입력값
    private String username;
    private String password;
    private String email;
    private String image;
    private String address;
    private String comment;


    private String role; //ROLE_USER, ROLE_ADMIN


}
