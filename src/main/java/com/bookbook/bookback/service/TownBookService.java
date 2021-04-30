package com.bookbook.bookback.service;

import com.bookbook.bookback.domain.controllerReturn.ResultReturn;


import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;

import com.bookbook.bookback.domain.repository.TownBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TownBookService {

    private final TownBookRepository townBookRepository;

    public ResultReturn getTownBook(){
        List<TownBook> townBookList = townBookRepository.findAll();
        return new ResultReturn(true, townBookList, "조회 성공");
    }

    public ResultReturn createTownBook(User user, TownBookDto townBookDto){
        String username = user.getUsername();
        townBookDto.setUsername(username);
        TownBook townBook = new TownBook(townBookDto,user);

        townBookRepository.save(townBook);
        return new ResultReturn(true,"등록 성공");
    }
//    @Transactional
//    public TownBook updateTownBook(Long townBookId, User user, TownBookDto townBookDto){
//        TownBook townBook = townBookRepository.findByTownBookId(townBookId);
//        if(!townBook.getUser().getUserId().equals(user.getUserId())){
//            throw new IllegalArgumentException("작성자만 수정가능합니다");
//        }
//        townBook.update(townBookDto);
//        return townBook;
//    }

//    public ResultReturn deleteTownBook(Long townBookId,User user){
//        TownBook townBook = townBookRepository.findByTownBookId(townBookId);
//        if(!townBook.getUser().getUserId().equals(user.getUserId())){
//            throw new IllegalArgumentException("작성자만 삭제가능합니다 ");
//        }
//        townBookRepository.deleteById(townBookId);
//        ReturnTownBook returnMsg =new ReturnTownBook();
//        returnMsg.setOk(true);
//        returnMsg.setMsg("리뷰삭제완료");
//        return returnMsg;
//    }



}
