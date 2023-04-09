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
@RequestMapping("study")
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
        return new ResponseEntity<>("Success study save", HttpStatus.BAD_REQUEST);
    }

    /**
     * 최근에 개설된 스터디 상위 15개 리스트
     */
    @GetMapping("/recruit")
    public Page<Study> recentStudy(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Study> studies = studyService.recentStudy(pageable);
        return studies;
    }

    /**
     * 검색어가 포함된 스터디 상위 15개 리스트
     */
    @GetMapping("/recruit/{keyword}")
    public List<Study> searchRecentStudy(@PageableDefault(size=15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @PathVariable String keyword){
        List<Study> studies = studyService.searchRecentStudy(keyword, pageable);
        return studies;
    }
}
