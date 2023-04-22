package capstone.backend.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 게시글 생성 폼
 */
@Data
public class PostingDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
