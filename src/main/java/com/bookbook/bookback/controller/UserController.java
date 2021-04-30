package com.bookbook.bookback.controller;


import com.bookbook.bookback.domain.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.controllerReturn.UserReturn;
import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserController {

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    @PostMapping("/api/signup")
    public ResultReturn join(@RequestBody UserDto userDto) {
        System.out.println("회원가입 진행 : " + userDto);
//        String rawPassword = userDto.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//        userDto.setPassword(encPassword);
//        userDto.setRole("ROLE_USER");

        try{
            userService.registerUser(userDto);

        }catch(IllegalArgumentException e){
            return new ResultReturn(false,e.getMessage() );

        }

        return new ResultReturn(true,"회원가입 완료");
    }

    // 로그인
    @PostMapping("/api/login")
    public UserReturn login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }


}
