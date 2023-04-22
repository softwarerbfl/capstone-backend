package capstone.backend.controller;


import capstone.backend.domain.Member;
import capstone.backend.domain.Posting;
import capstone.backend.domain.Study;
import capstone.backend.dto.PostingDto;
import capstone.backend.service.PostingService;
import capstone.backend.service.StudyService;
import capstone.backend.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posting")
public class PostingController {

    private final StudyService studyService;
    private final PostingService postingService;

    /**
     * 로그인 한 멤버가 가입한 스터디를 선택해서 글쓰기, 게시판 용도
     */
    @PostMapping("/write/{id}")
    public ResponseEntity<String> writePosting(@Valid @RequestBody PostingDto dto,
                                               BindingResult result,
                                               HttpServletRequest request,
                                               @PathVariable Long id) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("result error", HttpStatus.BAD_REQUEST);
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("no session", HttpStatus.BAD_REQUEST);
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return new ResponseEntity<>("no member", HttpStatus.BAD_REQUEST);
        }
        Study study = studyService.findByStudyId(id);
        postingService.save(dto, member, study);

        return new ResponseEntity<>("Success posting save", HttpStatus.OK);
    }

    /**
     * 로그인 한 멤버가 가입한 스터디들의 글을 조회할 때, 게시판에 나오는 것
     * 페이지 형태로 반환하게 됨
     */
    @GetMapping("/show")
    public Page<Posting> findByMember(HttpServletRequest request,
                                      @PageableDefault(size=15, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return postingService.findByMember(member, pageable);
    }

    /**
     * 스터디 아이디를 PathVariable로 주면 스터디의 모든 게시물을 반환
     * 페이지 형태로 반환하게 됨
     */
    @GetMapping("/{id}")
    public Page<Posting> findAll(@PathVariable Long id,
                                 @PageableDefault(size=15, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        Study study = studyService.findByStudyId(id);
        return postingService.findByStudy(study, pageable);
    }


}
