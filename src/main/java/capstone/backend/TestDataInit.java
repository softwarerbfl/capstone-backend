package capstone.backend;

import capstone.backend.domain.Category;
import capstone.backend.domain.Member;
import capstone.backend.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static capstone.backend.domain.Category.*;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        List<Category> list = new ArrayList<>();
        list.add(IT_PROGRAMMING);
        list.add(IT);
        list.add(CAREER);
        //디폴트 데이터 추가
        Member member = new Member();
        member.setAge(23L);
        member.setSex(1L);
        member.setEmail("32202018@dankook.ac.kr");
        member.setInterest(list);
        member.setBirth(LocalDate.of(2001, 11, 13));
        member.setMemberId("orange");
        member.setPassword("orange13!");
        member.setName("배규리");
        memberRepository.save(member);
        //디폴트 데이터 추가 2
        Member member2 = new Member();
        member2.setAge(1L);
        member2.setSex(0L);
        member2.setEmail("rbflrbfl@naver.com");
        member2.setInterest(list);
        member2.setBirth(LocalDate.of(2000, 11, 11));
        member2.setMemberId("rbfl");
        member2.setPassword("1234");
        member2.setName("귤귤");
        memberRepository.save(member2);
    }

}