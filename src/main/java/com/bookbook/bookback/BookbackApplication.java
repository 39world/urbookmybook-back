package com.bookbook.bookback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class BookbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookbackApplication.class, args);
    }

}
