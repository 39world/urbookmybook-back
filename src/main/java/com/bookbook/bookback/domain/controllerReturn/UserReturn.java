package com.bookbook.bookback.domain.controllerReturn;

import lombok.Data;


@Data
public class UserReturn {
    private Boolean ok;
    private String msg;
    private String token;
}
