package grooo.jpa_sample.api.board.dto;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class RequestBoard {
    private String title;
    private String content;

    public void basicValidate() {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD, "title");
        }
        if (title.length() > 100) {
            throw new CustomException(ErrorCode.MAX_SIZE_EXCEEDED, "title", 100);
        }
    }
}
