package capstone.backend.service;

import capstone.backend.domain.Category;
import capstone.backend.domain.Member;
import capstone.backend.domain.MemberStudy;
import capstone.backend.domain.Study;
import capstone.backend.dto.StudyDto;
import capstone.backend.repository.MemberStudyRepository;
import capstone.backend.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private MemberStudyRepository memberStudyRepository;

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

    /**
     * 스터디 멤버 찾기
     * 스터디에 가입한 멤버 리스트를 페이지 형태로 반환
     */
    public Page<Member> findMembersByStudyId(Study study, Pageable pageable){
        List<MemberStudy> memberStudyList = memberStudyRepository.findByStudy(study);

        List<Member> memberList = new ArrayList<>();
        for (int i=0; i<memberStudyList.size(); i++){
            memberList.add(memberStudyList.get(i).getMember());
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), memberList.size());
        final Page<Member> memberPage = new PageImpl<>(memberList.subList(start, end), pageable, memberList.size());
        return memberPage;

    }

    /**
     * 내 스터디 찾기
     * 멤버가 가입한 스터디 리스트들을 페이지 형태로 반환
     */
    public Page<Study> findStudyListByMember(Member member, Pageable pageable){
        // 멤버가 가입한 memberStudy 객체들을 먼저 찾고
        List<MemberStudy> memberStudyList = memberStudyRepository.findByMember(member);

        // 그 memberStudy 객체들에서 스터디들을 찾아서 가져오는 것
        List<Study> studyList = new ArrayList<>();
        for (int i=0; i<memberStudyList.size(); i++){
            studyList.add(memberStudyList.get(i).getStudy());
        }

        // 그 스터디 리스트를 페이지 형태로 바꿔서 반환
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), studyList.size());
        final Page<Study> studyPage = new PageImpl<>(studyList.subList(start, end), pageable, studyList.size());
        return studyPage;
    }

    /**
     * 스터디 가입
     * 멤버랑 스터디를 인자로 주면 멤버스터디 객체를 만드는데, 만약에 이미 가입한 이력이 있다면 추가로 저장하지 않음
     */
    public void joinStudy(Member member, Study study){
        // 새 memberStudy를 만들고
        MemberStudy memberStudy = new MemberStudy();
        memberStudy.setStudy(study);
        memberStudy.setMember(member);

        // 멤버가 가입한 스터디 리스트들을 가져온 후
        List<MemberStudy> memberStudyList = memberStudyRepository.findByMember(member);

        // 만약 그 스터디들 중 새로 가입하려는 스터디가 있다면 저장하지 않음
        for(int i=0; i<memberStudyList.size(); i++){
            if (memberStudyList.get(i).getStudy() == study){
                return;
            }
        }
        // 만약에 스터디 가입 이력이 없다면 저장
        memberStudyRepository.save(memberStudy);
    }

    /**
     * 모집글 찾기
     * 멤버를 인자로 주면 모집글을 찾아서 반환
     */
    public Page<String> findRecruitmentList(Pageable pageable){
        // 멤버가 속한 스터디들을 찾기
        List<Study> studyList = studyRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDateTime"));
        List<String> recruitmentList = new ArrayList<>();

        // for문을 돌면서 멤버가 가입한 스터디들의 introduce(recruitment, 모집글)를 리스트에 답기
        for(int i=0; i<studyList.size(); i++) {
            recruitmentList.add(studyList.get(i).getIntroduce());
        }

        // introduce 리스트를 페이지 형태로 반환
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), recruitmentList.size());
        final Page<String> recruitmentPage = new PageImpl<>(recruitmentList.subList(start, end), pageable, recruitmentList.size());
        return recruitmentPage;
    }
}
