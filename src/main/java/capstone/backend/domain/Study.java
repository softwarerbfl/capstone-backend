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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Member leader;

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Member> members = new ArrayList<>();
}
