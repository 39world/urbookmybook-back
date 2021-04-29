package com.bookbook.bookback.controller;


import com.bookbook.bookback.domain.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/api/signup")
    public ResultReturn join(@RequestBody UserDto userDto) {
        System.out.println("회원가입 진행 : " + userDto);
        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userDto.setPassword(encPassword);
        userDto.setRole("ROLE_USER");
//        여기서부터 수정 시작
//        userService.registerUser(userDto);
//
//        userRepository.save(user);
//        return "redirect:/";
    }
}
