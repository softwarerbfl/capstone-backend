package capstone.backend.controller;

import capstone.backend.domain.Member;
import capstone.backend.domain.Study;
import capstone.backend.dto.StudyDto;
import capstone.backend.repository.StudyRepository;
import capstone.backend.service.StudyService;
import capstone.backend.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Literal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/study")
public class StudyController {
    private final StudyService studyService;
    private final StudyRepository studyRepository;

    /**
     * 스터디 개설
     */
    @PostMapping("/new")
    public ResponseEntity<String> save(@Valid @RequestBody StudyDto dto,
                                       BindingResult result,
                                       HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("result error", HttpStatus.BAD_REQUEST);
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("no session", HttpStatus.BAD_REQUEST);
        }

        // 스터디를 개설하려는 사람(스터디장: 현재 로그인되어 있는 사람)
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return new ResponseEntity<>("no member", HttpStatus.BAD_REQUEST);
        }
        Study study = studyService.save(dto, member);

        //스터디 개설에 성공한 경우
        return new ResponseEntity<>("Success study save", HttpStatus.OK);
    }

    /**
     * 최근에 개설된 스터디 상위 8개 리스트
     */
    @GetMapping("/recruit")
    public Page<Study> recentStudy(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Study> studies = studyService.recentStudy(pageable);
        return studies;
    }

    /**
     * 검색어가 포함된 스터디 상위 8개 리스트
     */
    @GetMapping("/recruit/search/{keyword}")
    public List<Study> searchRecentStudy(@PageableDefault(size=8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @PathVariable String keyword){
        List<Study> studies = studyService.searchRecentStudy(keyword, pageable);
        return studies;
    }

    /**
     * 카테고리에 해당되는 스터디 상위 8개 리스트
     */
    @GetMapping("/recruit/{category}")
    public Page<Study> findByCategory(@PathVariable("category") String category,
                                      @PageableDefault(size=8, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Study> study = studyService.findByStudyCategory(category, pageable);
        return study;
    }
    /**
     * 스터디 아이디를 PathVariable로 주면 스터디를 찾아 스터디 객체를 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<Study> findByStudyId(@PathVariable Long id){
        Study study = studyService.findByStudyId(id);
        return ResponseEntity.status(HttpStatus.OK).body(study);
    }

    /**
     * 스터디 아이디를 PathVariable로 주면 스터디를 찾아 스터디 멤버 객체를 페이지 형태로 반환
     * 따로 반환하는 이유는 매핑때문에 @JsonIgnore 를 붙인 컬럼들은 위의 스터디 객체 반환 API 에서 안 뜨기 때문
     */
    @GetMapping("/{id}/members")
    public Page<Member> findMembersByStudyId(@PathVariable Long id,
                                             @PageableDefault(size=15, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Study study = studyService.findByStudyId(id);
        Page<Member> memberList = studyService.findMembersByStudyId(study, pageable);
        return memberList;
    }

    /**
     * 스터디 가입, {id}는 스터디의 id
     */
    @GetMapping("/join/{id}")
    public ResponseEntity<String> join(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new ResponseEntity<>("no session", HttpStatus.BAD_REQUEST);
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return new ResponseEntity<>("no member", HttpStatus.BAD_REQUEST);
        }
        Study study = studyService.findByStudyId(id);
        studyService.joinStudy(member, study);
        return new ResponseEntity<>("Success study join", HttpStatus.OK);
    }

    /**
     * 로그인 한 멤버의 스터디 리스트를 반환
     */
    @GetMapping("/my")
    public Page<Study> myStudyList(HttpServletRequest request,
                                   @PageableDefault(size=15, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Page<Study> studyList = studyService.findStudyListByMember(member, pageable);
        return studyList;
    }

    /**
     * 로그인 한 멤버가 가입한 스터디의 모집글 리스트를 페이지 형태로 반환
     */
    @GetMapping("/recruitment")
    public Page<String> myStudyRecruitmentList(@PageableDefault(size=15, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<String> studyList = studyService.findRecruitmentList(pageable);
        return studyList;
    }
}
