package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.ChatUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ChatMessage {

    public ChatMessage() {
    }



//    // 메시지 타입 : 입장, 퇴장, 채팅
//    public enum MessageType {
//        ENTER, QUIT, TALK
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

//    private MessageType type; // 메시지 타입
//
//    @Column
//    private String roomId; // 방번호
//
//    @Column
//    private String userName; // 메시지 보낸사람
//
//    private String userProfile;
//
//    @Column
//    private String message; // 메시지
//
//    @Column
//    private String timenow;

    @Column
    private String createdAt;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToOne
    @JoinColumn(name="chat_user_id")
    private ChatUser user;

}
