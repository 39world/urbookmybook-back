package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.FileUploadService;
import com.bookbook.bookback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder; //추후 패스워드 변경에서 사용 예정
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FileUploadService fileUploadService;

    //Request의 Header로 넘어온 token을 쪼개어 유저정보 확인해주는 과정
    @RequestMapping("/api/usercheck")
    public UserDto userInfo(HttpServletRequest httpServletRequest) {
    /*
    HTTP Request의 Header로 넘어온 token을 쪼개어 누구인지 나타내주는 과정
     */
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-MAIL이 없습니다"));
        return new UserDto(token,member);
    }

    //프로필 수정  multipart date-form 데이터를 받아 수정 진행
    //이 코드는 항상 file 데이터를 포함한 데이터가 와야함. 해결 가능할까 ?
//    @RequestMapping("/api/profile")
//    public UserDto profileChange(@RequestPart String userData, @RequestParam MultipartFile file, HttpServletRequest httpServletRequest) {
//        JSONObject userJson = new JSONObject(userData);
//        //토근에서 사용자 정보 추출
//        String token = jwtTokenProvider.resolveToken(httpServletRequest);
//        String email = jwtTokenProvider.getUserPk(token);
//        User member = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-MAIL이 없습니다"));
//        String fileUrl = fileUploadService.uploadImage(file);
//        //해당 사용자의 프로필 업데이트
//        UserDto userDto = new UserDto(member,userJson,fileUrl);
//        userService.update(userDto);
//        return userDto;
//    }

    //프로필 수정 파일 데이터 제외 버전.
    //프로필 사진이 있을 경우 파일 업로드를 진행 후 json 데이터에 반환된 파일 url을 넣어주면 사용 가능.
    @RequestMapping("/api/profile")
    public UserDto profileChange(@RequestBody String userData, HttpServletRequest httpServletRequest) {
        //토근에서 사용자 정보 추출
        JSONObject userJson = new JSONObject(userData);
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-MAIL이 없습니다"));
        //해당 사용자의 프로필 업데이트
        UserDto userDto = new UserDto(member,userJson);
        userService.update(userDto);
        return userDto;
    }
    //프로필 사진 등록 api
    //등록된 사진의 url을 반환
    @PostMapping("/api/upload")
    public String uploadImage(@RequestPart MultipartFile file) {
        return fileUploadService.uploadImage(file);
    }

    // 기능 테스트용 자체 회원가입, 로그인
    @PostMapping("/api/signup")
    public int join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .username(user.get("username"))
                .role("ROLE_USER")
                .build()).getId();
    }
    @PostMapping("/api/login")
    public UserDto login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getEmail());
        UserDto userDto = new UserDto(token,member);
        return userDto;
    }


}
