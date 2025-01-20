package grooo.jpa_sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import grooo.jpa_sample.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage;
        String exception = (String) request.getAttribute("exception");
        switch (exception) {
            case "ExpiredJwtException" -> errorMessage = "토큰이 만료되었습니다.";
            case "JwtException" -> errorMessage = "잘못된 토큰입니다.";
            case null, default -> errorMessage = "인증이 필요합니다.";
        }

        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
