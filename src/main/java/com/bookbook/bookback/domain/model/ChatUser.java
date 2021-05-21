package com.bookbook.bookback.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ChatUser {

    public ChatUser() {
    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long _id;

    @Column
    private String avatar;


    @Column
    private String name;

    @Column
    private String roomId;

    @Column
    private MessageType type; // 메시지 타입
}
