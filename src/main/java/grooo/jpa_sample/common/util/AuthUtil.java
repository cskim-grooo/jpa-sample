package grooo.jpa_sample.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthUtil {
    public static Long getAuthenticatedWorkerId(HttpServletRequest request) {
        Object workerIdObj = request.getAttribute("workerId");
        if (workerIdObj == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 정보가 없습니다.");
        }
        return (Long) workerIdObj;
    }
}
