package com.bookbook.bookback.controller;

import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.FileUploadService;
import com.bookbook.bookback.service.TownBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
public class TestController {
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;
    private final TownBookService townBookService;
    @PostMapping("/api/test")
    public String test(@RequestPart(required = false) MultipartFile file){

        System.out.println(file);
        if(file==null){
            return "file does not exist ";
        }

        String image= fileUploadService.uploadImage(file);
        System.out.println(image);
        return image;
    }

    //동네책장에 책 등록
    @PostMapping("/api/test/dto")
    public ResultReturn testDto(@RequestPart(required = false) MultipartFile file, @RequestPart(required = false) TownBookDto townBookDto){

        System.out.println(townBookDto);
        User user =userRepository.findById(1L).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않습니다.")
        );
        if(file==null){
            return new ResultReturn(false, "file이 존재하지 않습니다");
        }

        String image= fileUploadService.uploadImage(file);
        System.out.println(image);
        if(townBookDto==null){
            return new ResultReturn(false, "Dto가 존재하지 않습니다");
        }

        return new ResultReturn(true, "성공");

    }

    //동네책장에 책 등록
    @PostMapping(value= "/api/test/string", produces = "application/json; charset=utf8")
    public ResultReturn testString(@RequestPart(required = false) MultipartFile file, @RequestPart(required = false) String townBookDto) throws JsonProcessingException {

        System.out.println(townBookDto);
        User user =userRepository.findById(1L).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않습니다.")
        );

        if(file==null){
            return new ResultReturn(false, "file이 존재하지 않습니다");
        }

        String image= fileUploadService.uploadImage(file);
        System.out.println(image);
        if(townBookDto==null){
            return new ResultReturn(false, "Dto가 존재하지 않습니다");
        }
        System.out.println(townBookDto);

        ObjectMapper objectMapper = new ObjectMapper();
        TownBookDto dto = objectMapper.readValue(townBookDto, TownBookDto.class);

        return townBookService.createTownBook(user, dto, image);

    }

}
