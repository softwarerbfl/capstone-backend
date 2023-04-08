package capstone.backend.controller;

import capstone.backend.domain.Member;
import capstone.backend.service.LoginService;
import capstone.backend.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/test")
    public String test(
            @SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = true)
            Member loginMember){
        if (loginMember==null){
            return "fail session";
        }
        return "success, member name:"+loginMember.getName();

    }
}
