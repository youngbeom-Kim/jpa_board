package jpa.board.repository;

import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 게시판_등록() {

        //등록된 글 다 지우기
        //boardRepository.deleteAll();

        //given
        String title = "타이틀25";
        String content = "내용";

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        BoardDto boardDto = new BoardDto(title, content);
        Board board = boardDto.toEntity(member);
        boardRepository.save(board);

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board boards = boardList.get(0);
        assertThat(boards.getTitle()).isEqualTo(title);
        assertThat(boards.getContent()).isEqualTo(content);

    }

    @Test
    public void 게시판_수정() {

        //given
        List<Board> boardList = boardRepository.findAll();
        Board boards = boardList.get(0);
        String title = "변경된 타이틀";
        String content = "변경된 내용";

        boards.update(title, content);
        boardRepository.save(boards);

        //when
        List<Board> boardList2 = boardRepository.findAll();

        //then
        Board boards2 = boardList2.get(0);
        assertThat(boards2.getTitle()).isEqualTo(title);
        assertThat(boards2.getContent()).isEqualTo(content);

    }
}