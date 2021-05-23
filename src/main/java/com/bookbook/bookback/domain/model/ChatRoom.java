package com.bookbook.bookback.domain.model;

import com.bookbook.bookback.domain.dto.ChatRoomDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @ManyToMany
    @JoinColumn(name = "chet_room_user")
    private List<User> user = new ArrayList<>();

    @Column
    private String image;

    private long userCount; // 채팅방 인원수

    public static ChatRoom create(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = chatRoomDto.getRoomId();
        chatRoom.roomName = chatRoomDto.getRoomName();
        chatRoom.image = chatRoomDto.getImage();
        return chatRoom;
    }

    //roomname 삭제.
    //유저 닉네임, 유저 프로필 사진, 책 이미지 ?
    //유저 프로필 변경 시 같이 바꿔지도록 할 수 있을것인가
    //중복 채팅방 생성 방지
    //Redis를 이용한 실시간 알림 기능
    // 채팅방이 만들어졌거나 채팅이 왔을 때 사용자에게 알려줄 수 있도록.

}
