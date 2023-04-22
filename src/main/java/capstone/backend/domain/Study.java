package capstone.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "study")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @Column(name = "study_name")
    private String studyName;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "max_people")
    private Long maxPeople;

    @Column(name = "introduce")
    private String introduce;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Member leader;
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    // 이 스터디의 멤버들
    @JsonIgnore
    @OneToMany(mappedBy = "study")
    private List<MemberStudy> memberStudies = new ArrayList<>();

    // 이 스터디의 게시물들( 멤버가 스터디를 선택해서 글을 쓰므로)
    @JsonIgnore
    @OneToMany(mappedBy = "study")
    private List<Posting> postings = new ArrayList<>();

}
