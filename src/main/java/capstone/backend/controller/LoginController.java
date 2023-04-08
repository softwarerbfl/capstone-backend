package capstone.backend.controller;

import capstone.backend.domain.Member;
import capstone.backend.dto.LoginDto;
import capstone.backend.service.LoginService;
import capstone.backend.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto dto,
                                BindingResult result,
                                HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("result error", HttpStatus.BAD_REQUEST);
        }
        Member member = loginService.login(dto.getLoginId(), dto.getPassword());

        if (member == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다");
            return new ResponseEntity<>("id, password error", HttpStatus.BAD_REQUEST);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return new ResponseEntity<>("ok", HttpStatus.OK); // 로그인에 성공했으면 회원 이름 반환

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session!=null){
            session.invalidate();
        }
        return "logout";
    }
}
