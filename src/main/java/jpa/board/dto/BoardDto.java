package jpa.board.dto;

import jpa.board.entity.Board;
import jpa.board.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long id;            //시퀀스
    private String title;              //제목
    private String content;            //내용
    private LocalDateTime regDate;     //등록 날짜
    private LocalDateTime uptDate;     //수정 날짜
    private Long viewCount;            //조회수
    private String username;            //사용자 이름

    public BoardDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Board toEntity(Member member){
        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .build();
    }

}
