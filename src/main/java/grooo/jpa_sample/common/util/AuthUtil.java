package grooo.jpa_sample.common.util;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthUtil {
    public static Long getAuthenticatedWorkerId(HttpServletRequest request) {
        Object workerIdObj = request.getAttribute("workerId");
        if (workerIdObj == null) {
            throw new CustomException(ErrorCode.AUTH_NEED_TOKEN);
        }
        return (Long) workerIdObj;
    }
}
