package capstone.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 로그인 폼
 */
@Data
public class LoginDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
