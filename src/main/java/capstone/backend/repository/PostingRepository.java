package capstone.backend.repository;
import capstone.backend.domain.Member;
import capstone.backend.domain.MemberStudy;
import capstone.backend.domain.Posting;
import capstone.backend.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findByMember(Member member);

    List<Posting> findByStudy(Study study);

}
