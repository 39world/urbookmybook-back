package com.bookbook.bookback.controller;


import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class TestController {

    private final FileUploadService fileUploadService;
    //Post man에서는 data에 townBookDto를 append할 때 ',' 후 {contentType: 'application/json'}을 붙여줘야 한글이 깨지지 않는다.
    @PostMapping("/api/testbooks")
    public ResultReturn getAllBooks(@RequestPart String townBookDto, @RequestPart List<MultipartFile> files) {


        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String image = fileUploadService.uploadImage(file);
            images.add(image);
        }

        System.out.println("images: "+ images);

        if(townBookDto==null){
            return new ResultReturn(false, "실패");
        }

        System.out.println("Dto: "+ townBookDto);;

        return new ResultReturn(true, "성공");
    }
}
