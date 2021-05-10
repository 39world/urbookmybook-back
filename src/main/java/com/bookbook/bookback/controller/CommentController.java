package com.bookbook.bookback.controller;

import com.bookbook.bookback.config.security.JwtTokenProvider;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.CommentDto;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.UserRepository;
import com.bookbook.bookback.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //댓글 등록
    @PostMapping("/api/comments/{townBookId}")
    public ResultReturn create(@RequestBody CommentDto commentDto, @PathVariable Long townBookId, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        return commentService.createComment(commentDto, townBookId, user);

    }

    //댓글 수정
    @PutMapping("/api/comments/{commentId}")
    public ResultReturn update(@RequestBody CommentDto commentDto, @PathVariable Long commentId, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        return commentService.updateComment(commentDto, commentId, user);
    }

    //댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResultReturn delete(@PathVariable Long commentId, HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        return commentService.deleteComment(commentId, user);
    }

    @GetMapping("/api/comments")
    public ResultReturn getMyComment ( HttpServletRequest httpServletRequest){
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        return commentService.getMyComment(user);
    }


}
