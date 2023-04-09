package capstone.backend.service;

import capstone.backend.domain.Category;
import capstone.backend.domain.Member;
import capstone.backend.domain.Study;
import capstone.backend.dto.StudyDto;
import capstone.backend.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    /**
     * 스터디 개설
     */
    public Study save(StudyDto dto, Member member){
        Study study = new Study();
        study.setStudyName(dto.getName());
        study.setLeader(member);
        study.setCategory(Category.valueOf(dto.getCategory()));
        studyRepository.save(study);
        return study;
    }

    /**
     * 스터디 모집 - 페이징 처리
     */
    public Page<Study> recentStudy(Pageable pageable){
        return studyRepository.findAll(pageable);
    }
}
