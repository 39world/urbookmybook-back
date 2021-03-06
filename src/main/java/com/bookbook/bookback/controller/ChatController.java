package com.bookbook.bookback.controller;


import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.domain.model.ChatMessage;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.ChatMessageRepository;
import com.bookbook.bookback.domain.repository.ChatRoomRepository;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.ChatRoomService;
import com.bookbook.bookback.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

//@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {//ChatService에서 입/퇴장을 처리하기 때문에 간소

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * websocket "/pub/api/chat/message"로 들어오는 메시징을 처리한다.
     */
    //채팅방에 접속하면서 채팅방에 저장된 메세지를 불러오는 api
    @GetMapping("/api/chat/message/{roomId}")
    @ResponseBody
    public List<ChatMessage> loadMessage(@PathVariable String roomId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByRoomIdOrderByTimenowDesc(roomId);
        return messages;
    }
    //가장 최근에 채팅방에서 전송된 메세지 확인
    @GetMapping("/api/chat/message/last/{roomId}")
    public ChatMessage lastMessage(@PathVariable String roomId){
        ChatMessage message = chatMessageRepository.findFirstByRoomIdOrderByTimenowDesc(roomId);
        return message;
    }


    @MessageMapping("/api/chat/message") // 웹소켓으로 들어오는 메시지 발행 처리 -> 클라이언트에서는 /pub/api/chat/message로 발행 요청
    public void message(@RequestBody ChatMessage message, @Header("token") String token) {
        String email = jwtTokenProvider.getUserPk(token); //회원의 대화명을 가져와 token 유효성 체크
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-MAIL이 없습니다"));
        String nickname = member.getUsername();
        message.setEmail(email);
        // 헤더에서 토큰을 읽어 로그인 회원 정보로 대화명 설정
        message.setUserName(nickname);
        System.out.println("토큰 유효성 확인 완료, 해당 닉네임 : "+ nickname);
        // 채팅방 인원수 세팅
        System.out.println(message);
        long systemTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String dTime = formatter.format(systemTime);
        message.setTimenow(dTime);
        System.out.println("DB 저장 완료");
        chatMessageRepository.save(message);
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message); // 메서드 일원화
        System.out.println("메세지 송부 요청 완료");
    }
}