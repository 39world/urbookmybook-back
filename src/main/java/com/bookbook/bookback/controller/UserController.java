package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.CommentService;
import com.bookbook.bookback.service.FileUploadService;
import com.bookbook.bookback.service.TownBookService;
import com.bookbook.bookback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder; //추후 패스워드 변경에서 사용 예정
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TownBookService townBookService;
    private final CommentService commentService;
    private final FileUploadService fileUploadService;

    //Request의 Header로 넘어온 token을 쪼개어 유저정보 확인해주는 과정
    @RequestMapping("/api/usercheck")
    public ResultReturn userInfo(HttpServletRequest httpServletRequest) {
    /*
    HTTP Request의 Header로 넘어온 token을 쪼개어 누구인지 나타내주는 과정
     */
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저체크, 일치하는 E-MAIL이 없습니다"));
        UserDto userDto = new UserDto(token, user);
        return new ResultReturn(true, userDto,"프로필 조회 완료");
    }


//    //프로필 사진이 있을 경우 파일 업로드를 진행 후 json 데이터에 반환된 파일 url을 넣어주면 사용 가능.
    @RequestMapping("/api/profile")
    public ResultReturn profileChange(@RequestBody String userData, HttpServletRequest httpServletRequest) {
        //토근에서 사용자 정보 추출
        JSONObject userJson = new JSONObject(userData);
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 E-MAIL이 없습니다"));
        //해당 사용자의 프로필 업데이트
        UserDto userDto = new UserDto(member,userJson);
        userService.update(userDto);
        return new ResultReturn(true, userDto,"프로필 변경 완료");
    }
//    //프로필 사진 등록 api
//    //등록된 사진의 url을 반환
    @PostMapping("/api/upload")
    public ResultReturn uploadImage(@RequestPart(required = false) MultipartFile file) {
        if(file == null){
            return new ResultReturn(true, "이미지 파일이 없습니다.");
        } else{
            return new ResultReturn(true, fileUploadService.uploadImage(file),"이미지 파일 등록 완료.");
        }
    }

    //google social login test code
    @PostMapping("/api/login")
    public ResultReturn loginUser(@RequestBody UserDto userDto ) {
        log.info("email:{}, username:{}, imaege:{}",userDto.getEmail(),userDto.getUsername(),userDto.getImage());
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = jwtTokenProvider.createToken(user.getEmail());
            return new ResultReturn(true, token, "회원가입이 된 사람입니다.");
        } else {
            User user = new User(userDto);
            userRepository.save(user);
            String token = jwtTokenProvider.createToken(user.getEmail());
            return new ResultReturn(true, token, "로그인이 되었습니다.");
        }
    }

    //내가 등록한 게시글 조회
    @GetMapping("/api/users/townbooks")
    public ResultReturn getMyTownBooks ( HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("내가 쓴 게시글 조회, 아이디가 존재하지 않습니다.")
        );
        return townBookService.getMyTownBooks(user);
    }

    //내가 쓴 댓글 조회
    @GetMapping("/api/users/comments")
    public ResultReturn getMyComments (HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("내 댓글 조회, 아이디가 존재하지 않습니다.")
        );
        return commentService.getMyComments(user);
    }

    //관심 있는 책 리스트 조회
    @GetMapping("/api/users/scraps")
    public ResultReturn getMyScrapList(HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("스크랩 조회, 아이디가 존재하지 않습니다.")
        );

        return userService.getMyScrapList(user);
    }

    //관심 있는 책 삭제
    @DeleteMapping("/api/users/scraps/{townBookId}")
    public ResultReturn deleteMyScrapList(@PathVariable Long townBookId, HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("스크랩 삭제, 아이디가 존재하지 않습니다.")
        );

        return userService.deleteMyScrapList(townBookId, user);
    }

}
