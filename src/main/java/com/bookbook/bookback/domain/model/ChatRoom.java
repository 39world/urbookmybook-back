package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.ChatRoomDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ChatRoom implements Serializable { // redis에 저장되는 객체들은 Serialize가 가능해야 함, -> Serializable 참조

    private static final long serialVersionUID = 6494678977089006639L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @ElementCollection
    private List<Long> chatUser;

    private long userCount; // 채팅방 인원수

    public static ChatRoom create(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = chatRoomDto.getRoomName();
        chatRoom.chatUser = chatRoomDto.getChatUser();
        return chatRoom;
    }

}
