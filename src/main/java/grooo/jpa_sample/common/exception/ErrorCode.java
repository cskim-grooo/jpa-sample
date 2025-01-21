package grooo.jpa_sample.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    WRONG_DATA_FIELD(HttpStatus.BAD_REQUEST, "validation.wrongData.field"),
    MAX_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "validation.maxSize.exceeded"),
    REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "validation.required.field"),

    NOT_FOUND_RECORD(HttpStatus.NOT_FOUND,  "error.recordNotFound"),
    UNHANDLED_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error.unhandled"),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "error.request.invalid_format"),

    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"error.auth.tokenExpired"),
    AUTH_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "error.auth.permissionDenied"),
    AUTH_TOKEN_UNAVAILABLE(HttpStatus.UNAUTHORIZED, "error.auth.tokenUnavailable"),
    AUTH_NEED_TOKEN(HttpStatus.UNAUTHORIZED, "error.auth.needToken"),

    SIGNUP_EXIST_LOGIN_ID(HttpStatus.BAD_REQUEST ,"error.signup.existLoginId"),
    PASSWORD_EQUALS_LOGIN_ID(HttpStatus.BAD_REQUEST, "error.signup.equalsLoginId"),
    EXPIRY_DATE_BEFORE_NOW(HttpStatus.BAD_REQUEST, "error.signup.expiryDateBeforeNow"),
    NOT_EXIST_LOGIN_ID(HttpStatus.BAD_REQUEST ,"error.login.notExistLoginId"),
    LOGIN_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "error.login.passwordMiss");

    private final HttpStatus status;
    private final String messageKey;

    ErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }
}
