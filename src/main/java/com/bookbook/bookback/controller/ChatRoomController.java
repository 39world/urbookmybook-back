package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.ChatRoomDto;
import com.bookbook.bookback.domain.model.ChatRoom;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.ChatRoomRepository;
import com.bookbook.bookback.service.ChatRoomService;
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
@Controller
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

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
        //토근에서 사용자 정보 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByChatUser(email);
        return new ResultReturn(true, chatRooms,"참여중인 채팅방 조회 완료");
    }


    //채팅방 생성(parameter : roomName, userInterested)
    @PostMapping("/create")
    @ResponseBody
    public ResultReturn createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoomDto);
        chatRoomRepository.save(createdRoom);
        return new ResultReturn(true, createdRoom,"채팅방 생성 완료");
    }

    //특정 채팅방 입장
    @GetMapping("/enter/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }
}


