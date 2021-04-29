package com.bookbook.bookback.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//ConfigurationProperties(prefix="cloud.aws.s3")을 통해 프로퍼티의 값을 불러오도록 합니다.
// @Setter 필요, 또한 S3ServiceImpl에서 빈 주입 받기 위해 빈으로 등록
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.s3")
@Component
public class S3Component {

    private String bucket;

}