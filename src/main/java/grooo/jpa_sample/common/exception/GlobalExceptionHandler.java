package grooo.jpa_sample.common.exception;

import grooo.jpa_sample.common.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final TranslationService translationService;

    // CustomException 직접 처리한 에러 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errCode = ex.getErrorCode();

        Object[] localizedArgs = translationService.translateStringArgs(ex.getArgs());
        String localizedMessage = translationService.translateMessage(errCode.getMessageKey(), localizedArgs);

        return ResponseEntity.status(errCode.getStatus())
                .body(new ErrorResponse(errCode.getStatus().value(), localizedMessage));
    }

    // @RequestBody 누락/파싱 실패 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), translationService.translateMessage(ErrorCode.INVALID_REQUEST_BODY.getMessageKey())));
    }
    // 그 외 예외 처리 (잡지못한 예외)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), translationService.translateMessage(ErrorCode.UNHANDLED_SERVER_ERROR.getMessageKey())));
    }
}
