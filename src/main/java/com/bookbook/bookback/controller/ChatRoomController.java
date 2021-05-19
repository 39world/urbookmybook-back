package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.ChatRoomDto;
import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.ChatMessage;
import com.bookbook.bookback.domain.model.ChatRoom;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.ChatMessageRepository;
import com.bookbook.bookback.domain.repository.ChatRoomRepository;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.ChatRoomService;
import com.bookbook.bookback.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    //기존에 쓰이던 채팅방 리스트 조회 hash 사용
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();
//        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomId())));
//        return chatRooms;
//    }
    //참여중인 채팅방 조회
    @GetMapping("/rooms")
    public ResultReturn profileChange(HttpServletRequest httpServletRequest){
        //토큰에서 사용자 정보 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByChatUser(email);
        return new ResultReturn(true, chatRooms,"참여중인 채팅방 조회 완료");
    }


    //채팅방 생성(parameter : roomName, user_email)
    @PostMapping("/create")
    public ResultReturn createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoomDto);
        chatRoomRepository.save(createdRoom);
        return new ResultReturn(true, createdRoom,"채팅방 생성 완료");
    }

    //특정 채팅방 입장. 채팅방에 저장된 메세지 반환
    @GetMapping("/enter/{roomId}")
    public ResultReturn roomInfo(@PathVariable String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByRoomIdOrderByTimenowDesc(roomId);
        return new ResultReturn(true, messages,"채팅방 입장, 메세지 조회 완료");
    }

    //채팅방 퇴장 테스트 필요
    @PutMapping("/quit/{roomId}")
    public ResultReturn quitRoom(@PathVariable String roomId, HttpServletRequest httpServletRequest){
        ChatRoom chatRoom  = chatRoomService.findRoomById(roomId);
        //토큰에서 사용자 정보 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저체크, 일치하는 E-MAIL이 없습니다"));
        //유저 목록에서 제거
        chatRoom.getUser().remove(user);
        //남아있는 유저가 없을 경우 DB에서 삭제
        if(chatRoom.getUser().isEmpty()){
            chatRoomRepository.delete(chatRoom);
        }
        else{
            //유저가 남아있다면 나가는 유저 정보를 가져와서 채팅방에 남아있는 인원에게 퇴장 메세지 전송
            chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.QUIT).roomId(roomId).userName(user.getUsername()).build());
        }
        return new ResultReturn(true,"채팅방 나가기 완료");
    }


}


