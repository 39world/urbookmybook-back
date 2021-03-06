package com.bookbook.bookback.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomDto {
    private String roomName;
    private List<String> chatUser;
    private String image;
    private String roomId;
    private Long townBookId;
    
}
