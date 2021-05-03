package com.bookbook.bookback.service;


import com.bookbook.bookback.controllerReturn.DetailReturn;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.CommentRepository;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TownBookService {

    private final TownBookRepository townBookRepository;
    private final CommentRepository commentRepository;

    //동네 책장 전체 조회
    public ResultReturn getTownBooks(){
        List<TownBook> townBookList = townBookRepository.findAll();

        return new ResultReturn(true, townBookList, "동네책방 리스트 반환 성공!");
    }

    public ResultReturn createTownBook(User user,TownBookDto townBookDto, List<String> captureImages){
        townBookDto.setUser(user);
        townBookDto.setCaptureImages(captureImages);
        TownBook townBook = new TownBook(townBookDto);
        townBookRepository.save(townBook);
        return new ResultReturn(true, "동네책방 등록 성공!");

    }
    //등록한 책 정보 수정
    @Transactional
    public ResultReturn updateTownBook(Long townBookId, TownBookDto townBookDto, User user){
        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                () -> new IllegalArgumentException("책이 존재하지 않습니다.")
        );

        if(user==null)
            return new ResultReturn(false, "로그인이 필요한 서비스입니다.");

        if(!townBook.getUser().getId().equals(user.getId())){
            return new ResultReturn(false,"수정은 작성자 본인만 가능합니다.");
        }
        else{
            townBook.update(townBookDto);
            return new ResultReturn(true,"수정이 완료되었습니다.");
        }



    }

    //등록한 책 삭제
    public ResultReturn deleteTownBook(Long townBookId,User user){
        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                () -> new IllegalArgumentException("책이 존재하지 않습니다.")
        );
        if(!townBook.getUser().getId().equals(user.getId())){
            return new ResultReturn(false,"삭제는 작성자 본인만 가능합니다.");
        }
        else{
            townBookRepository.deleteById(townBookId);
            return new ResultReturn(true,"삭제가 완료되었습니다.");
        }
    }

    public DetailReturn detailTownBook(Long townBookId){
        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                ()-> new IllegalArgumentException("책이 존재하지 않습니다")
        );
        List<Comment> comments = commentRepository.findByTownBookId(townBookId);
        return new DetailReturn(true, townBook, comments, "성공!");

    }


}
