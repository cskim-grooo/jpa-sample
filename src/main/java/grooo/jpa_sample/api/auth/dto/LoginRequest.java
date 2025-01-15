package grooo.jpa_sample.api.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class LoginRequest {
    private String loginId;
    private String password;

    public void basicValidate() {
        if (loginId == null || loginId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LoginID는 필수값 입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password는 필수값 입니다.");
        }
    }
}
