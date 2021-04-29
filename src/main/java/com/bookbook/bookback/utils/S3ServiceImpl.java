package com.bookbook.bookback.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3Client;
    private final S3Component s3Component;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    //aws-cloud-starter-aws 라이브러리에서 제공하는 AmazonS3Client를 사용해서 다음과 같이 파일을 업로드 하고(uploadFile),
    //아래 메소드는 업로드한 파일의 URI를 가져오는 메소드입니다.

    @Override
    public String getFileUrl(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(s3Component.getBucket(), fileName));
    }
    //S3Component를 주입받아서 프로퍼티에 추가한 버킷 정보를 가져와서 awsS3Client.putObject(...)를 통해 AWS S3로 파일을 업로드합니다.
    //그리고 awsClient.getUrl(...)을 통해 업로드된 파일의 URI을 가져옵니다. 다음은 -> FileUploadService

}