package com.bookbook.bookback.config;

import com.bookbook.bookback.config.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker //stomp를 사용하기 위함
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) { // 상속받은 WebSocketMessageBrokerConfigurer 인터페이스 사용
        config.enableSimpleBroker("/sub"); // pub sub 메시징 구현 - sub
        config.setApplicationDestinationPrefixes("/pub"); // prefix - pub
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*://*")//endpoint, 개발 서버 접속 주소 : 토큰을 가지고 http://localhost:8080/ws-stomp로 접속하면 Handshake 일어나면서 HTTP->WS 프로토콜 변결. "Welcome to SockJS!"가 출력됨
                .withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 합니다.
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler); // StompHandler가 Websocket 앞단에서 token을 체크할 수 있도록 인터셉터로 설정
    }
}