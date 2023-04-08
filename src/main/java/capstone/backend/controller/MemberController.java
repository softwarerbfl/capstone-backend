package capstone.backend.controller;

import capstone.backend.domain.Member;
import capstone.backend.repository.MemberRepository;
import capstone.backend.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @PostMapping("/add")
    public String save(@Valid @RequestBody Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "error!";
        }
        memberRepository.save(member);
        return "save member: " + member.getMemberId() + member.getPassword();
    }

    /**
     * 마이 페이지
     */
    @GetMapping("/member")
    public Member myPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session==null){
            log.info("no session");
            return null;
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(member ==null){
            log.info("no member");
            return null;
        }
        return member;
    }
}
