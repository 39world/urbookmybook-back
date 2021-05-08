package com.bookbook.bookback.controller;


import com.bookbook.bookback.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {
    final private FileUploadService fileUploadService;

    @PostMapping("/api/image")
    public String uploadImage(@RequestPart MultipartFile file){
        String image= fileUploadService.uploadImage(file);
        return image;
    }

    @PostMapping("/api/images")
    public List<String> uploadImages(@RequestPart List<MultipartFile> files) {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String image = fileUploadService.uploadImage(file);

            System.out.println("captureImage: " + image);

            images.add(image);
        }
        return images;
    }
}
