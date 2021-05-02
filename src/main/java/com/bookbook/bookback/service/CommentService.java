package com.bookbook.bookback.service;


import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.CommentDto;
import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.CommentRepository;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TownBookRepository townBookRepository;

    //댓글 등록
    public ResultReturn createComment(CommentDto commentDto,Long townBookId,User user){
        if(user==null){
            return new ResultReturn(false, "로그인이 필요한 서비스입니다.");
        }

        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                ()->new IllegalArgumentException("책이 존재하지 않습니다.")
        );
        commentDto.setTownBook(townBook);
        commentDto.setUser(user);
        Comment comment=new Comment(commentDto);
        commentRepository.save(comment);
        return new ResultReturn(true, "댓글 작성이 완료되었습니다.");

    }


    //댓글 수정
    @Transactional
    public ResultReturn updateComment(CommentDto commentDto,Long commentId,User user){
        if(user==null){
            return new ResultReturn(false, "로그인이 필요한 서비스입니다.");
        }
//        commentDto.setId(commentId);
//        commentDto.setUser(user);
        Comment comment =commentRepository.findById(commentId).orElseThrow(
                ()->new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        if(!comment.getUser().getId().equals((user.getId()))){
            return new ResultReturn(false, "수정은 작성자 본인만 가능합니다.");
        }
        else{
            comment.update(commentDto);
            return new ResultReturn(true, "수정이 완료되었습니다.");
        }
    }

    //댓글 삭제
    public ResultReturn deleteComment(Long commentId, User user){
        //로그인 확인
        if(user==null)
            return new ResultReturn(false, "로그인이 필요한 서비스입니다.");

        Comment comment =commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        //작성자 본인이 아닌 경우
        if(!comment.getUser().getId().equals(user.getId()))
            return new ResultReturn(false, "삭제는 작성자 본인만 가능합니다.");

            //작성한 후기 삭제
        else{
            commentRepository.deleteById(commentId);
            return new ResultReturn(true, "삭제가 완료되었습니다.");
        }
    }

}
