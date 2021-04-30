package com.bookbook.bookback.service;


import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void registerUser(UserDto userDto) {

        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userDto.setPassword(encPassword);
        userDto.setRole("ROLE_USER");

//        String pattern = "^[0-9]*$";
        String email = userDto.getEmail();


//        if(username.length()<3){
//            throw new IllegalArgumentException("닉네임은 최소 3자 이상으로 구성돼야 합니다.");
//        }
//
//        if(userDto.getPassword().length()<4){
//            throw new IllegalArgumentException("패스워드는 최소 4자 이상으로 구성돼야 합니다.");
//        }
//        else if(userDto.getPassword().contains((username))){
//            throw new IllegalArgumentException("패스워드에 닉네임이 포함되어있습니다.");
//        }

        // 닉네임 중복 확인
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }


        userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(email)
                .role(userDto.getRole())
                .image(userDto.getImage())
                .address(userDto.getAddress())
                .comment(userDto.getComment())
                .build());
    }
}
