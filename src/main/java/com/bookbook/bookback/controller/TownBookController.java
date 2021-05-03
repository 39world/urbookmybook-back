package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.controllerReturn.DetailReturn;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.FileUploadService;
import com.bookbook.bookback.service.TownBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class TownBookController {
    private final TownBookService townBookService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    //동네책장 전체 조회
    @GetMapping("/api/townbooks")
    public ResultReturn getTownBooks(){

        return townBookService.getTownBooks();
    }

    //동네책장에 책 등록
    @PostMapping("/api/townbooks")
    public ResultReturn createTownBook(@RequestParam List<MultipartFile> files, @RequestParam(required = false) TownBookDto townBookDto, HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않습니다.")
        );

//        if(townBookDto==null){
//            return new ResultReturn(false, "실패!");
//        }

        System.out.println(townBookDto.getAuthor()+ townBookDto.getCategory());
        List <String> captureImages=new ArrayList<>();
        for (MultipartFile file : files){
            String image= fileUploadService.uploadImage(file);
            captureImages.add(image);
        }
        System.out.println(captureImages);
        return townBookService.createTownBook(user, townBookDto, captureImages);

    }

    //등록한 책 정보 수정
    @PutMapping("/api/townbooks/{townBookId}")
    public ResultReturn updateTownBook(@PathVariable Long townBookId, @RequestBody TownBookDto townBookDto, HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않습니다.")
        );
        return townBookService.updateTownBook(townBookId,townBookDto, user);
    }

    //등록한 책 삭제
    @DeleteMapping("/api/townbooks/{townBookId}")
    public ResultReturn deleteTownBook(@PathVariable Long townBookId,HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않습니다.")
        );
        return townBookService.deleteTownBook(townBookId, user);
    }


    //상세 페이지 정보 조회
    @GetMapping("/api/townbooks/{townBookId}")
    public DetailReturn detail(@PathVariable Long townBookId) {
        return townBookService.detailTownBook(townBookId);
    }

}
