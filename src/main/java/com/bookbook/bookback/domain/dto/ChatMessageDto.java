package com.bookbook.bookback.domain.dto;


import lombok.Data;

@Data
public class ChatMessageDto {
    private String _id;
    private String createdAt;
    private String text;
    private ChatUserDto user;

}
