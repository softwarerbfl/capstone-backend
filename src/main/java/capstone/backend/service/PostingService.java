package capstone.backend.service;


import capstone.backend.domain.*;
import capstone.backend.dto.PostingDto;
import capstone.backend.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PostingService {
    @Autowired
    private PostingRepository postingRepository;

    /**
     * 게시글 Dto랑 멤버 그리고 스터디를 인자로 주면 새로운 게시글을 만들어서 리턴
     */
    public void save(PostingDto dto, Member member, Study study){
        Posting posting = new Posting();
        posting.setTitle(dto.getTitle());
        posting.setContent(dto.getContent());
        posting.setMember(member);
        posting.setStudy(study);
        postingRepository.save(posting);
        return;
    }

    /**
     * 멤버가 속한 스터디의 게시글 찾기
     */
    public Page<Posting> findByMember(Member member, Pageable pageable){
        List<Posting> postingList = postingRepository.findByMember(member);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), postingList.size());
        final Page<Posting> postingPage = new PageImpl<>(postingList.subList(start, end), pageable, postingList.size());

        return postingPage;
    }

    /**
     * 스터디 게시글 찾기
     */
    public Page<Posting> findByStudy(Study study, Pageable pageable){
        List<Posting> postingList = postingRepository.findByStudy(study);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), postingList.size());
        final Page<Posting> postingPage = new PageImpl<>(postingList.subList(start, end), pageable, postingList.size());

        return postingPage;
    }


}