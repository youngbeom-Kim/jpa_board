package jpa.board.controller;

import jpa.board.dto.BoardDto;
import jpa.board.repository.CustomBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final CustomBoardRepository customBoardRepository;

    @GetMapping("/")
    public String list(String searchVal, Pageable pageable, Model model) {
        Page<BoardDto> results = customBoardRepository.selectBoardList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        pageModelPut(results, model);
        return "board/list";
    }

    private void pageModelPut(Page<BoardDto> results, Model model) {
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @GetMapping("/update")
    public String upadte() {
        return "board/update";
    }
}
