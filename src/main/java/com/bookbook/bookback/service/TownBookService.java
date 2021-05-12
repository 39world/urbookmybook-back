package com.bookbook.bookback.service;


import com.bookbook.bookback.controllerReturn.DetailReturn;
import com.bookbook.bookback.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.PartCommentDto;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.Comment;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.domain.repository.CommentRepository;
import com.bookbook.bookback.domain.repository.TownBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TownBookService {

    private final TownBookRepository townBookRepository;
    private final CommentRepository commentRepository;

    //교환할 책 등록
    public ResultReturn createTownBook(User user,TownBookDto townBookDto){
        townBookDto.setUser(user);
        townBookDto.setTown(user.getTown());
        townBookDto.setViews(0);
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

        if(townBookDto.getCaptureImages() == null){
            townBookDto.setCaptureImages(townBook.getCaptureImages());
        }

        if(!townBook.getUser().getId().equals(user.getId())){
            return new ResultReturn(false,"수정은 작성자 본인만 가능합니다.");
        }

        else{
            townBook.setTown(user.getTown());
            townBook.update(townBookDto);
            return new ResultReturn(true,"수정이 완료되었습니다.");
        }


    }

    //등록한 책 삭제
    @Transactional
    public ResultReturn deleteTownBook(Long townBookId,User user){
        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                () -> new IllegalArgumentException("책이 존재하지 않습니다.")
        );
        if(!townBook.getUser().getId().equals(user.getId())){
            return new ResultReturn(false,"삭제는 작성자 본인만 가능합니다.");
        }
        else{
            commentRepository.deleteByTownBookId(townBookId);
            townBookRepository.deleteById(townBookId);
            return new ResultReturn(true,"삭제가 완료되었습니다.");
        }
    }

    @Transactional
    public DetailReturn detailTownBook(Long townBookId){
        TownBook townBook = townBookRepository.findById(townBookId).orElseThrow(
                ()-> new IllegalArgumentException("책이 존재하지 않습니다")
        );
        //방문 시 조회 수 증가
        Integer views= townBook.getViews();
        townBook.setViews(views+1);

        List<PartCommentDto> partComments = new ArrayList<>();


        List<Comment> comments = commentRepository.findByTownBookId(townBookId);
        for(Comment comment : comments){
            Long commentId= comment.getId();
            String username= comment.getUsername();
            String contents= comment.getContents();
            String image = comment.getUser().getImage();

            String email=comment.getUser().getEmail();
            Long userId=comment.getUser().getId();

            partComments.add(new PartCommentDto(commentId,username,contents, image, email, userId));
        }

        return new DetailReturn(true, townBook, partComments, "상세 정보 반환을 성공했습니다.");

    }

    public ResultReturn getTownBooks(User user, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TownBook> townBookList= townBookRepository.findByTownOrderByCreatedAtDesc(user.getTown(), pageable);

        if(townBookList.isEmpty())
            return new ResultReturn(false, "등록된 책이 없습니다.");
        else
            return new ResultReturn(true, townBookList, "동네책장 반환을 완료했습니다.");
    }

//    public ResultReturn getTownBooks(){
//        List<TownBook> townBookList= townBookRepository.findAll();
//        if(townBookList.isEmpty()){
//            return new ResultReturn(false, "등록된 책이 없습니다.");
//        }
//        else
//            return new ResultReturn(true, townBookList, "반환 완료");
//    }

    //제목을기반으로 검색하기
    public List<TownBook> search(String keyword){
        List<TownBook> townBookList = townBookRepository.findByTitleContainingOrderByModifiedAtDesc(keyword);


        return townBookList;
    }

    //카테고리별 검색하기
    public ResultReturn searchByCategory(String category){
        List <TownBook> books= townBookRepository.findByCategoryContainingOrderByModifiedAtDesc(category);
        if(books.isEmpty())
            return new ResultReturn(false, "등록된 책이 없습니다.");
        else
            return new ResultReturn(true, books, "카테고리 별 동네책장 반환을 완료했습니다.");

    }

    //ㄴㅐ가쓴글(?)
    public ResultReturn getMyTownBook(User user) {

        List<TownBook> townBookList = townBookRepository.findByUserEmail(user.getEmail());


        return new ResultReturn(true ,townBookList, "댓글 반환 성공!");
    }


}
