package jpa.board.controller;

import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.repository.CustomBoardRepository;
import jpa.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final CustomBoardRepository customBoardRepository;
    private final BoardService boardService;

    @GetMapping("/")
    public String list(String searchVal, Pageable pageable, Model model) {
        Page<BoardDto> results = customBoardRepository.selectBoardList(searchVal, pageable);
        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchVal", searchVal);

        pageModelPut(results, model);
        return "board/list";
    }

    private void pageModelPut(Page<BoardDto> results, Model model) {
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "board/write";
    }

    @PostMapping("/write")
    public String save(@Valid BoardDto boardDto, BindingResult result) {

        //유효성검사 걸릴 시
        if (result.hasErrors()) {
            return "board/write";
        }

        boardService.saveBoard(boardDto);
        return "redirect:/";
    }

    @GetMapping("/update/{boardId}")
    public String detail(@PathVariable Long boardId, Model model) {
        Board board = boardService.selectBoardDetail(boardId);
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardId);
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        model.addAttribute("boardDto", boardDto);

        return "board/update";
    }

    @PutMapping("/update/{boardId}")
    public String update(@Valid BoardDto boardDto, BindingResult result) {
        //유효성검사 걸릴 시
        if (result.hasErrors()) {
            return "board/update";
        }

        boardService.saveBoard(boardDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam List<String> boardIds) {
        for(int i = 0; i < boardIds.size(); i++) {
            Long id = Long.valueOf(boardIds.get(i));
            boardService.deleteBoard(id);
        }

        return "redirect:/";
    }
}
