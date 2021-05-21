package com.bookbook.bookback.domain.dto;

import com.bookbook.bookback.domain.model.ChatMessage;
import lombok.Data;

@Data
public class ChatUserDto {
    private Long _id;
    private String avatar;
    private String name;
    private String roomId;
    private int type;
}
