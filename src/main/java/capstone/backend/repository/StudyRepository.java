package capstone.backend.repository;

import capstone.backend.domain.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    // 제목에 검색어를 포함하는 스터디 리스트 페이징
    List<Study> findByStudyNameContaining(String keyword, Pageable pageable);
}
