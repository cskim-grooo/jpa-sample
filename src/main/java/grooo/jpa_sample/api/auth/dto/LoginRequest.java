package grooo.jpa_sample.api.auth.dto;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String loginId;
    private String password;

    public void basicValidate() {
        if (loginId == null || loginId.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "loginId");
        }
        if (password == null || password.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "password");
        }
    }
}
