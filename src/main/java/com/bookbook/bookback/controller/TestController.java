package com.bookbook.bookback.controller;


import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.service.FileUploadService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class TestController {

    private final FileUploadService fileUploadService;
    //Post man에서는 data에 townBookDto를 append할 때 ',' 후 {contentType: 'application/json'}을 붙여줘야 한글이 깨지지 않는다.
    @PostMapping("/api/testbooks")
    public ResultReturn getAllBooks(@RequestPart String townBookDto, @RequestPart List<MultipartFile> files) throws UnsupportedEncodingException {


        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String image = fileUploadService.uploadImage(file);
            images.add(image);
        }
        System.out.println("images: "+ images);

        if(townBookDto==null){
            return new ResultReturn(false, "실패");
        }

//        System.out.println("Dto: "+ townBookDto);


//        System.out.println("1. utf-8 -> euc-kr        : " + new String(townBookDto.getBytes("utf-8"), "euc-kr"));
//        System.out.println("2. utf-8 -> ksc5601       : " + new String(townBookDto.getBytes("utf-8"), "ksc5601"));
//        System.out.println("3. utf-8 -> x-windows-949 : " + new String(townBookDto.getBytes("utf-8"), "x-windows-949"));
//        System.out.println("4. utf-8 -> iso-8859-1    : " + new String(townBookDto.getBytes("utf-8"), "iso-8859-1"));
//        System.out.println("5. iso-8859-1 -> euc-kr        : " + new String(townBookDto.getBytes("iso-8859-1"), "euc-kr"));
//        System.out.println("6. iso-8859-1 -> ksc5601       : " + new String(townBookDto.getBytes("iso-8859-1"), "ksc5601"));
//        System.out.println("7. iso-8859-1 -> x-windows-949 : " + new String(townBookDto.getBytes("iso-8859-1"), "x-windows-949"));
        System.out.println("iso-8859-1 -> utf-8         : " + new String(townBookDto.getBytes("iso-8859-1"), "utf-8"));
//        System.out.println("9. euc-kr -> utf-8         : " + new String(townBookDto.getBytes("euc-kr"), "utf-8"));
//        System.out.println("10. euc-kr -> ksc5601       : " + new String(townBookDto.getBytes("euc-kr"), "ksc5601"));
//        System.out.println("11. euc-kr -> x-windows-949 : " + new String(townBookDto.getBytes("euc-kr"), "x-windows-949"));
//        System.out.println("12. euc-kr -> iso-8859-1    : " + new String(townBookDto.getBytes("euc-kr"), "iso-8859-1"));
//        System.out.println("13. ksc5601 -> euc-kr        : " + new String(townBookDto.getBytes("ksc5601"), "euc-kr"));
//        System.out.println("14. ksc5601 -> utf-8         : " + new String(townBookDto.getBytes("ksc5601"), "utf-8"));
//        System.out.println("15. ksc5601 -> x-windows-949 : " + new String(townBookDto.getBytes("ksc5601"), "x-windows-949"));
//        System.out.println("16. ksc5601 -> iso-8859-1    : " + new String(townBookDto.getBytes("ksc5601"), "iso-8859-1"));
//        System.out.println("17. x-windows-949 -> euc-kr     : " + new String(townBookDto.getBytes("x-windows-949"), "euc-kr"));
//        System.out.println("18. x-windows-949 -> utf-8      : " + new String(townBookDto.getBytes("x-windows-949"), "utf-8"));
//        System.out.println("19. x-windows-949 -> ksc5601    : " + new String(townBookDto.getBytes("x-windows-949"), "ksc5601"));
//        System.out.println("20. x-windows-949 -> iso-8859-1 : " + new String(townBookDto.getBytes("x-windows-949"), "iso-8859-1"));



        return new ResultReturn(true, "성공");



    }
}
