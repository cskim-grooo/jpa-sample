package grooo.jpa_sample.api.board.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class RequestBoard {
    private String title;
    private String content;

    public void basicValidate() {
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title : 필수값 입니다.");
        }
        if (title.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title : 100자 이하여야 합니다.");
        }
    }
}
