package com.bookbook.bookback.controller;


import com.bookbook.bookback.domain.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.CommentDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import com.bookbook.bookback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final TownBookRepository townBookRepository;
    private final CommentService commentService;

@PostMapping("/api/comments/{townBookId}")
public ResultReturn create(@RequestBody CommentDto commentDto, @PathVariable Long townBookId, @AuthenticationPrincipal User user){
    if(user==null){
        return new ResultReturn(false, "로그인이 필요한 서비스입니다.");
    }


    TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
            ()->new IllegalArgumentException("책이 존재하지 않습니다.")
    );
    commentDto.setTownBook(townBook);
    commentDto.setUser(user);
    commentService.createComment(commentDto);
    return new ResultReturn(true, "댓글 작성이 완료되었습니다.");
}

@PutMapping("/api/comments/{commentId}")
public ResultReturn update(@RequestBody CommentDto commentDto, @PathVariable Long commentId, @AuthenticationPrincipal User user){
    if(user==null){
        return new ResultReturn(false, "로그인이 필요한 서비스입니다.");
    }

    commentDto.setId(commentId);
    commentDto.setUser(user);

    if(commentService.updateComment(commentDto)){
        return new ResultReturn(true, "수정이 완료되었습니다.");
    }
    else{
        return new ResultReturn(false, null, "수정은 작성자 본인만 가능합니다.");
    }
}


}
