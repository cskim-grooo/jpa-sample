package grooo.jpa_sample.api.auth.dto;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SignupRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime expiryDate;
    private List<Long> roleIds;

    public void basicValidate() {
        validateRequiredFields();
        validateFieldLengths();
    }

    private void validateRequiredFields() {
        if (loginId == null || loginId.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "loginId");
        }
        if (password == null || password.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "password");
        }
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "name");
        }
        if (email == null || email.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "email");
        }
        if (expiryDate == null) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "expiryDate");
        }
        if (roleIds == null || roleIds.isEmpty()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "roleIds");
        }
    }

    private void validateFieldLengths() {
        if (loginId.length() > 30) {
            throw new CustomException(ErrorCode.MAX_SIZE_EXCEEDED, "loginId", 30);
        }
        if (password.length() > 500) {
            throw new CustomException(ErrorCode.MAX_SIZE_EXCEEDED, "password", 500);
        }
        if (name.length() > 500) {
            throw new CustomException(ErrorCode.MAX_SIZE_EXCEEDED, "name", 500);
        }
        if (email.length() > 255) {
            throw new CustomException(ErrorCode.MAX_SIZE_EXCEEDED, "email", 255);
        }
        for (Long roleId : roleIds) {
            if (roleId == null || roleId <= 0) {
                throw new CustomException(ErrorCode.WRONG_DATA_FIELD, "roleIds");
            }
        }
    }
}
