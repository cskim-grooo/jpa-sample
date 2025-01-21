package grooo.jpa_sample.common.exception;

import lombok.Getter;
import org.springframework.context.MessageSource;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] args;

    public CustomException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = args;
    }
}
