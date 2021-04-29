package com.bookbook.bookback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //시간 자동변경이 가능하도록 한다.
@SpringBootApplication
public class BookbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookbackApplication.class, args);
    }

}
