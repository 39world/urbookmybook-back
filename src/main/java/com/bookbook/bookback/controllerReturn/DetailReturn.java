package com.bookbook.bookback.controllerReturn;

import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.TownBook;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailReturn {

    private Boolean ok;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TownBook townBook;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> comments;
    private String msg;

    public DetailReturn(Boolean ok, String msg){
        this.ok=ok;
        this.msg=msg;
    }

    public DetailReturn(Boolean ok, TownBook townBook, List<Comment> comments, String msg){
        this.ok = ok;
        this.townBook=townBook;
        this.comments=comments;
        this.msg = msg;
    }
}
