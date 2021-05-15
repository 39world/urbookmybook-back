package com.bookbook.bookback.service;

import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.UserDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import com.bookbook.bookback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TownBookRepository townBookRepository;
//
//    @Transactional
//    public User loginUser(UserDto userDto) {
//        if (userRepository.findByEmail(userDto.getUsername()).orElse(null) != null) {
//            throw new NullPointerException("이미 가입되어 있는 유저입니다.");
//        }
//        User user = new User(userDto);
//        return userRepository.save(user);
//    }

    @Transactional
    public ResultReturn update(UserDto userDto,User user){
        //프로필 변경 시 타운이 변경되면 해당 유저가 등록한 책들의 타운값도 변경해준다.
        if(userDto.getTown()!=null){
            List<TownBook> townBookList= townBookRepository.findByUser(user);
            for( TownBook townBook : townBookList){
                townBook.setTown(userDto.getTown());
            }
        }

        user.update(userDto);
        return new ResultReturn(true, "수정 선공");
    }

    @Transactional
    public ResultReturn getMyScrapList(User user){
        List <TownBook> scrapList= new ArrayList<>();
        List <Long> deleteTownBookIdList= new ArrayList<>();
        List<Long> townBookIdList= user.getScrapList();
        for(Long townBookId : townBookIdList){
            TownBook townBook = townBookRepository.findById(townBookId).orElse(
                    null
            );
            if(townBook==null)
                deleteTownBookIdList.add(townBookId);

            else
                scrapList.add(townBook);
        }

        for(Long townBookId: deleteTownBookIdList){
            townBookIdList.remove(townBookId);
        }

        if(scrapList.isEmpty())
            return new ResultReturn(false, "관심 있는 책이 없습니다");

        else
            return new ResultReturn(true, scrapList, "관심 있는 책 리스트 반환 완료");
    }

    @Transactional
    public ResultReturn deleteMyScrapList(Long townBookId, User user){
            List<Long> townBookIdList= user.getScrapList();
            if(townBookId==null)
                return new ResultReturn(false, "책이 존재하지 않습니다.");
            else{
                townBookIdList.remove(townBookId);
                return new ResultReturn(true,"삭제 완료");
            }

    }

}