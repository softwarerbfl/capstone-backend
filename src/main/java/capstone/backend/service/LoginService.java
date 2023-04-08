package capstone.backend.service;

import capstone.backend.domain.Member;
import capstone.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 입력받은 아이디와 비번이 일치하면 member객체를 return
     */
    public Member login(String loginId, String password){

        //로그인 아이디에 해당하는 멤버 객체 찾아옴
        Optional<Member> login = Optional.ofNullable(memberRepository.findByMemberId(loginId));

        //찾아온 멤버의 비번과 입력받은 비번이 일치하면 member 리턴
        return login.filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
