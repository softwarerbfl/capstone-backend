package capstone.backend.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_study")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberStudy {
    @Id @GeneratedValue
    @Column(name = "member_study_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

}
