package capstone.backend.dto;
import capstone.backend.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 스터디 생성 폼
 */
@Data
public class StudyDto {
    @NotEmpty
    private String name;

    @NotEmpty
    private String category;

    @NotEmpty
    private Long maxPeople;

    private String introduce;
}