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
    public void update(UserDto userDto){
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        user.update(userDto);
    }

    @Transactional
    public ResultReturn getMyWishList(User user){
        List <TownBook> wishList= new ArrayList<>();
        List <Long> deleteTownBookIdList= new ArrayList<>();
        List<Long> townBookIdList= user.getWishList();
        for(Long townBookId : townBookIdList){
            TownBook townBook = townBookRepository.findById(townBookId).orElse(
                    null
            );
            if(townBook==null)
                deleteTownBookIdList.add(townBookId);

            else
                wishList.add(townBook);
        }

        for(Long townBookId: deleteTownBookIdList){
            townBookIdList.remove(townBookId);
        }

        if(wishList.isEmpty())
            return new ResultReturn(false, "관심 있는 책이 없습니다");

        else
            return new ResultReturn(true, wishList, "관심 있는 책 리스트 반환 완료");
    }

    @Transactional
    public ResultReturn deleteMyWishList(Long townBookId, User user){
            List<Long> townBookIdList= user.getWishList();
            if(townBookId==null)
                return new ResultReturn(false, "책이 존재하지 않습니다.");
            else{
                townBookIdList.remove(townBookId);
                return new ResultReturn(true,"삭제 완료");
            }

    }

}