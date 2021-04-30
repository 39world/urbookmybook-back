package com.bookbook.bookback.controller;

import com.bookbook.bookback.domain.controllerReturn.ResultReturn;
import com.bookbook.bookback.domain.dto.TownBookDto;
import com.bookbook.bookback.domain.model.TownBook;
import com.bookbook.bookback.domain.model.User;
import com.bookbook.bookback.service.TownBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TownBookController {
    private final TownBookService townBookService;

    @GetMapping("/api/townbook")
    public ResultReturn getTownBook(){

        return townBookService.getTownBook();

    }

    @PostMapping("/api/townbook/{id}")
    public ResultReturn createTownBook(@RequestBody TownBookDto townBookDto, @AuthenticationPrincipal User user){
        return townBookService.createTownBook(user, townBookDto);
    }

//    @PutMapping("/api/townbook/{townBookId}")
//    public TownBook updateTownBook(@PathVariable Long townBookId, @RequestBody TownBookDto townBookDto, @AuthenticationPrincipal User user){
//        return townBookService.updateTownBook(townBookId,user, townBookDto);
//    }
//
//    @DeleteMapping("/api/townbook/{townBookId}")
//    public ResultReturn deleteTownBook(@PathVariable long townBookId,@AuthenticationPrincipal User user) {
//        return townBookService.deleteTownBook(townBookId, user);
//    }
}
