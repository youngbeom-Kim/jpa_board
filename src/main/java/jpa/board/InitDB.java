package jpa.board;

import jpa.board.entity.Member;
import jpa.board.repository.BoardRepository;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

import static jpa.board.entity.Authority.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.userDBInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;
        public void userDBInit(){

            List<Member> memberList = memberRepository.findAll();
            if(memberList.size() == 0){
                Member member = Member.builder()
                        .username("관리자")
                        .phoneNo("010-1234-5678")
                        .age(26)
                        .authority(ROLE_ADMIN)
                        .build();
                //member 저장
                memberRepository.save(member);
            }
        }
    }
}
