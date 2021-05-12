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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
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

//    동네책장 전체 조회
    @GetMapping("/api/townbooks")
    public ResultReturn getAllBooks( @RequestParam("page") int page, HttpServletRequest httpServletRequest) {

        int size=10;
        String sortBy="CreatedAt";
        boolean isAsc= false;
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("전체 조회, 아이디가 존재하지 않습니다.")
        );

        page = page - 1;
        return townBookService.getTownBooks(user, page , size, sortBy, isAsc);
    }

//    @GetMapping("/api/townbooks")
//    public ResultReturn getAllBooks(){
//        return townBookService.getTownBooks();
//
//    }
    //동네책장에 책 등록
    @PostMapping("/api/townbooks")
    public ResultReturn createTownBook(@RequestBody TownBookDto townBookDto, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("책 등록, 아이디가 존재하지 않습니다.")
        );

//        User user =userRepository.findById(1L).orElseThrow(
//                ()->new IllegalArgumentException("존재하지 않습니다.")
//        );
//

        if(townBookDto==null){
            return new ResultReturn(false, "Dto가 존재하지 않습니다");
        }

        return townBookService.createTownBook(user, townBookDto);
    }

    //등록한 책 정보 수정
    @PutMapping("/api/townbooks/{townBookId}")
    public ResultReturn updateTownBook(@PathVariable Long townBookId, @RequestBody TownBookDto townBookDto, HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("책 정보 수정, 아이디가 존재하지 않습니다.")
        );
        return townBookService.updateTownBook(townBookId,townBookDto, user);
    }

    //등록한 책 삭제
    @DeleteMapping("/api/townbooks/{townBookId}")
    public ResultReturn deleteTownBook(@PathVariable Long townBookId,HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("책 삭제, 아이디가 존재하지 않습니다.")
        );
        return townBookService.deleteTownBook(townBookId, user);
    }


    //상세 페이지 정보 조회
    @GetMapping("/api/townbooks/{townBookId}")
    public DetailReturn detail(@PathVariable Long townBookId) {
        return townBookService.detailTownBook(townBookId);
    }



    //검색기능
    @GetMapping("/api/townbooks/search")
    public List<TownBook> searchByTitle(@RequestParam(value = "keyword")String keyword){
        return townBookService.searchByTitle(keyword);
    }

     //카테고리 별 책 검색
    @PostMapping("/api/townbooks/category")
    public ResultReturn searchByCategory(@RequestBody String category){

        return townBookService.searchByCategory(category);

    }

    //관심책 등록하기
    @PostMapping("/api/townbooks/scraps/{townBookId}")
    public ResultReturn putToMyWishList(@PathVariable Long townBookId, HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("스크랩 등록, 아이디가 존재하지 않습니다.")
        );
        return townBookService.putToMyScrapList(townBookId, user);
    }

    //교환 완료 처리
    @PutMapping("/api/townbooks/finish/{townBookId}")
    public ResultReturn finishTownBook(@PathVariable Long townBookId, @RequestBody String userData){
        JSONObject userInfo = new JSONObject(userData);
        User masterUser = userRepository.findById(userInfo.getLong("masterUser")).orElseThrow(
                ()->new IllegalArgumentException("master 유저가 존재하지 않습니다.")
        );
        User otherUser = userRepository.findById(userInfo.getLong("otherUser")).orElseThrow(
                ()->new IllegalArgumentException("other 유저가 존재하지 않습니다.")
        );
        return townBookService.finishTownBook(townBookId,masterUser,otherUser);

    }

}
