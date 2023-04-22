package capstone.backend.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "posting")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long id;

    private String title; // 글의 제목

    private String content; // 글의 내용

    // 멤버가 선택한 스터디
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    // 글을 쓴 멤버
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}