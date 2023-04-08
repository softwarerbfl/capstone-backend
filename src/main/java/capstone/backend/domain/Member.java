package capstone.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId; //중복 안되도록!

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private Long age;
    private Long sex; //남자는 0,여자는 1
    private LocalDate birth;
    private List<Category> interest;
    private String email;

}
