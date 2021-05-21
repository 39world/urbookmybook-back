package com.bookbook.bookback.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Hashtable;

@Getter
@Setter
@Entity
public class ChatMessage {

    public ChatMessage() {
    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String userName, String userProfile, String message) {
        this.type = type;
        this.roomId = roomId;
        this.userName = userName;
        this.userProfile = userProfile;
        this.message = message;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type; // 메시지 타입

    @Column
    private String roomId; // 방번호

    @Column
    private String userName; // 메시지 보낸사람

    private String userProfile;

    @Column
    private String message; // 메시지

    @Column
    private String timenow;

    @Column
    private String email;
}
