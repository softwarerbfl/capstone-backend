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
import java.util.List;
import java.util.Optional;

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
        study.setIntroduce(dto.getIntroduce());
        study.setMaxPeople(dto.getMaxPeople());
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
    /**
     * 스터디 모집 - 페이징 처리(검색어 있을 경우)
     */
    public List<Study> searchRecentStudy(String keyword, Pageable pageable){
        return studyRepository.findByStudyNameContaining(keyword, pageable);
    }

    /**
     * 스터디 모집 - 페이징 처리( 카테고리 필터링)
     */
    public Page<Study> findByStudyCategory(String category, Pageable pageable){
        Category strToCategory = Category.valueOf(category);
        Page<Study> studyPage = studyRepository.findByCategory(strToCategory, pageable);
        return studyPage;
    }
    /**
     * 스터디 고유 id로 Study 객체 찾기
     */
    public Study findByStudyId(Long id){
        Optional<Study> study = studyRepository.findById(id);
        return study.orElse(null);
    }
}
