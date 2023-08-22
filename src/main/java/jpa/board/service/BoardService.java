package jpa.board.service;

import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.repository.BoardRepository;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Board selectBoardDetail(Long id) {
        Board board = boardRepository.findById(id).get();
        board.updateViewCount(board.getViewCount());
        return board;
    }

    @Transactional
    public Long saveBoard(BoardDto boardDto) {
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        Board board = null;

        //insert
        if (boardDto.getId() == null) {
            board = boardDto.toEntity(member);
            boardRepository.save(board);
        } else { //update
            board = boardRepository.findById(boardDto.getId()).get();
            board.update(boardDto.getTitle(), boardDto.getContent());
        }

        return board.getId();
    }

    @Transactional
    public Board deleteBoard(Long id) {
        Board board = boardRepository.findById(id).get();

        //플래그값이 Y이면 논리삭제
        board.delete("Y");
        return board;
    }

}
