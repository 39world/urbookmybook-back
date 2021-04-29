package com.bookbook.bookback.utils;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

//인터페이스인 S3Service를 FileUploadService에서 주입받아서 런타임 시점에 오브젝트 관계를 갖도록 해줌
public interface S3Service {

    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);

}