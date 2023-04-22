package capstone.backend.repository;

import capstone.backend.domain.Member;
import capstone.backend.domain.MemberStudy;
import capstone.backend.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {
    List<MemberStudy> findByMember(Member member);

    List<MemberStudy> findByStudy(Study study);


}
