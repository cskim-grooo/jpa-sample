package grooo.jpa_sample.api.auth.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Getter
public class SignupRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime expiryDate;

    public void basicValidate() {
        validateRequiredFields();
        validateFieldLengths();
    }

    private void validateRequiredFields() {
        if (loginId == null || loginId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LoginID는 필수값입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password는 필수값입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name은 필수값입니다.");
        }
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email은 필수값입니다.");
        }
        if (expiryDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "만료일자는 필수값입니다.");
        }
    }

    private void validateFieldLengths() {
        if (loginId.length() > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LoginID는 30자 이하여야 합니다.");
        }
        if (password.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password는 500자 이하여야 합니다.");
        }
        if (name.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name은 500자 이하여야 합니다.");
        }
        if (email.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email은 255자 이하여야 합니다.");
        }
    }
}
