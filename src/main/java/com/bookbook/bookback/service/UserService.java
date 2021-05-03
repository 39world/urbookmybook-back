package com.bookbook.bookback.service;

import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
//
//    @Transactional
//    public User loginUser(UserDto userDto) {
//        if (userRepository.findByEmail(userDto.getUsername()).orElse(null) != null) {
//            throw new NullPointerException("이미 가입되어 있는 유저입니다.");
//        }
//        User user = new User(userDto);
//        return userRepository.save(user);
//    }

    @Transactional
    public void update(UserDto userDto){
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        user.update(userDto);
    }
}